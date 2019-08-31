package pe.edu.pucp.perugopf.presentation.activities.create_event_firestore;

import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.data.entities.NewEvent;
import pe.edu.pucp.perugopf.domain.create_event_firestore_interactor.ICreateEventFirestoreInteractor;
import pe.edu.pucp.perugopf.presentation.activities.event_detail_firestore.EventDetailFirestorePresenter;
import pe.edu.pucp.perugopf.presentation.utils.PhotoUtils;

public class CreateEventFirestorePresenter implements ICreateEventFirestoreContract.IPresenter {

    ICreateEventFirestoreContract.IView view;

    @Inject
    ICreateEventFirestoreInteractor interactor;
    @Inject
    FirebaseAuth firebaseAuth;
    @Inject
    FirebaseStorage storage;

    @Inject
    EventDetailFirestorePresenter detallePresenter; // RAM

    @Inject
    public CreateEventFirestorePresenter() {
    }

    @Override
    public void attachView(ICreateEventFirestoreContract.IView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void createEvent(String title, String content, String path, String direccion, String fecha, String latitud, String longitud, String indAprobado) {
        view.showProgressDialog();
        if(TextUtils.isEmpty(title) || content.isEmpty()){
            if(isViewAttached()) view.showError("Ingrese datos porfavor");
            return;
        }
        if(path != null && !path.isEmpty()){
            StorageReference storageReference = storage.getReference();
            String name = "JPEG_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_.jpg";
            StorageReference imageReference = storageReference.child("images/" + name);
            UploadTask uploadTask = imageReference.putBytes(PhotoUtils.getBytesPhoto(path));
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return imageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri donwloadUri = task.getResult();
                        createNewEvent(title, content, donwloadUri.toString(), direccion, fecha, latitud, longitud, indAprobado);
                    }else {
                        if(isViewAttached()) view.showError("Ocurrio un error al subir la imagen");
                        createNewEvent(title,content,null, direccion, fecha, latitud, longitud, indAprobado);
                    }
                }
            });
        } else {
            createNewEvent(title,content,null, direccion, fecha, latitud, longitud, indAprobado);
        }

    }

    private void createNewEvent(String title, String content, String path, String direccion, String fecha, String latitud, String longitud, String indAprobado){
        NewEvent event = new NewEvent();
        event.setTitle(title);
        event.setContent(content);
        event.setDireccion(direccion);
        event.setFecha(fecha);
        event.setLatitud(latitud);
        event.setLongitud(longitud);
        event.setIndAprobado(indAprobado);
        event.setUserUid(firebaseAuth.getUid());

        if(path != null && !path.isEmpty()) {
            event.setPathPhoto(path);
        }
        interactor.createEvent(event, task -> {
            if(isViewAttached()) {
                view.hideProgressDialog();
                if (task.isSuccessful()) {
                    view.onSuccessCreate();
                } else {
                    view.showError(task.getException().getMessage());
                }
            }
        });
    }
}


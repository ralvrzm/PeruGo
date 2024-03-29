package pe.edu.pucp.perugopf.presentation.activities.event_detail_firestore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.R;
import pe.edu.pucp.perugopf.base.BaseActivity;
import pe.edu.pucp.perugopf.data.entities.NewEvent;
import pe.edu.pucp.perugopf.di.components.DaggerPresentationComponent;
import pe.edu.pucp.perugopf.di.modules.PresentationModule;

public class EventDetailFirestoreActivity extends BaseActivity implements IEventDetailFirestoreContract.IView {

    ImageView ivPhoto;
    TextView tvTitulo;
    TextView tvDescripcion;
    TextView tvDireccion;
    TextView tvFecha;
    ProgressBar pbLoading;
    private FloatingActionButton fabRefuse;
    private FloatingActionButton fabApprove;
    Button btnmaps;

    String idEvent;

    private static final String ESTADO_APROBADO = "1";
    private static final String ESTADO_RECHAZADO = "-1";

    @Inject
    EventDetailFirestorePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_event_detail_firestore;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        presenter.attachView(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        idEvent = getIntent().getStringExtra("idEvent");

        ivPhoto = findViewById(R.id.ivPhoto);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvFecha = findViewById(R.id.tvFecha);
        pbLoading = findViewById(R.id.pb_loading);
        btnmaps = findViewById(R.id.btn_maps);

        fabRefuse = findViewById(R.id.fab_refuse);
        fabApprove = findViewById(R.id.fab_approve);

        // Estos botones actualizarán el estado del evento
        fabApprove.setOnClickListener(v -> {
            presenter.updateEvent(idEvent, ESTADO_APROBADO);
            Toast.makeText(getApplicationContext(), "Evento aprobado.", Toast.LENGTH_SHORT).show();
            onSuccessCreate();
        });

        fabRefuse.setOnClickListener(v -> {
            presenter.updateEvent(idEvent, ESTADO_RECHAZADO);
            Toast.makeText(getApplicationContext(), "Evento rechazado.", Toast.LENGTH_SHORT).show();
            onSuccessCreate();
        });

        presenter.getEvent(idEvent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerPresentationComponent.builder()
                .applicationComponent(getApplicationComponent())
                .presentationModule(new PresentationModule())
                .build().inject(this);
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void getEventDetailSuccess(NewEvent event) {
        if(event.getPathPhoto() != null && !event.getPathPhoto().isEmpty()){
            Glide.with(getApplicationContext())
                    .load(event.getPathPhoto())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.logo_perugo_white))
                    .into(ivPhoto);
        } else {
            ivPhoto.setVisibility(View.GONE);
        }
        tvTitulo.setText(event.getTitle());
        tvDescripcion.setText(event.getContent());
        tvDireccion.setText(event.getDireccion());
        tvFecha.setText(event.getFecha());

        btnmaps.setOnClickListener(v -> {
            //Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + "-15.017005" + "," + "-73.780980" + "(" + "coracora" + ")");
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + event.getLatitud() + "," + event.getLongitud() + "(" + event.getTitle() + ")");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
    }

    @Override
    public void hideProgressDialog() {
        hideDialog();
    }

    @Override
    public void onSuccessCreate() {
        // TODO: Revisar, cuando se llama esto se cae, debe ser que no tiene referencia a la lista de eventos
        // Luego de actualizar el evento regresa a la lista de eventos.
        presenter.showEvents();
        //finish();
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onDetachedFromWindow() {
        presenter.detachView();
        super.onDetachedFromWindow();
    }
}

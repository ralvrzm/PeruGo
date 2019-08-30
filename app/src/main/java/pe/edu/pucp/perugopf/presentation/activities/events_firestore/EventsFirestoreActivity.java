package pe.edu.pucp.perugopf.presentation.activities.events_firestore;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.R;
import pe.edu.pucp.perugopf.base.BaseActivity;
import pe.edu.pucp.perugopf.data.entities.NewEvent;
import pe.edu.pucp.perugopf.di.components.DaggerPresentationComponent;
import pe.edu.pucp.perugopf.di.modules.PresentationModule;
import pe.edu.pucp.perugopf.presentation.activities.create_event_firestore.CreateEventFirestoreActivity;
import pe.edu.pucp.perugopf.presentation.activities.event_detail_firestore.EventDetailFirestoreActivity;
import pe.edu.pucp.perugopf.presentation.adapters.NewEventAdapter;
import pe.edu.pucp.perugopf.presentation.utils.AdapterClickListener;

public class EventsFirestoreActivity extends BaseActivity implements IEventsFirestoreContract.IView{

    private TextView tvNoEvents;
    private FloatingActionButton fabAdd;
    private RecyclerView recyclerViewEvents;
    private ProgressBar progressBarMain;
    private NewEventAdapter newEventAdapter;
    private List<NewEvent> newEventList;

    @Inject
    EventsFirestorePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_events_firestore;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        presenter.attachView(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tvNoEvents = findViewById(R.id.tv_no_events);
        fabAdd = findViewById(R.id.fab_add);
        recyclerViewEvents = findViewById(R.id.recyclerViewEvents);
        progressBarMain = findViewById(R.id.progressBarMain);
        setAdapter();
        setListeners();
        presenter.getAllEvent();
    }

    private void setAdapter(){
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));
        newEventList = new ArrayList<>();
        newEventAdapter = new NewEventAdapter(newEventList);
        newEventAdapter.setOnItemClickListener(new AdapterClickListener() {
            @Override
            public void onClick(int position) {
                gotToDetailEvent(newEventList.get(position).getId());
            }
        });
        recyclerViewEvents.setAdapter(newEventAdapter);
    }

    private void setListeners(){
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CreateEventFirestoreActivity.class);
            startActivity(intent);
        });
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
        progressBarMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarMain.setVisibility(View.GONE);
    }

    @Override
    public void showNoEvent() {
        tvNoEvents.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoEvent() {
        tvNoEvents.setVisibility(View.GONE);
    }

    @Override
    public void refreshRecyclerView(List<NewEvent> eventList) {
        this.newEventList.clear();
        this.newEventList.addAll(eventList);
        newEventAdapter.notifyDataSetChanged();
    }

    @Override
    public void gotToDetailEvent(String eventUid) {
        Intent intent = new Intent(this, EventDetailFirestoreActivity.class);
        intent.putExtra("idEvent", eventUid);
        startActivity(intent);
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

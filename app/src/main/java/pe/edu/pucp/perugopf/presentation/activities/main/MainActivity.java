package pe.edu.pucp.perugopf.presentation.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.R;
import pe.edu.pucp.perugopf.base.BaseActivity;
import pe.edu.pucp.perugopf.data.entities.Event;
import pe.edu.pucp.perugopf.di.components.DaggerPresentationComponent;
import pe.edu.pucp.perugopf.di.modules.PresentationModule;
import pe.edu.pucp.perugopf.presentation.activities.event_detail.EventDetailActivity;
import pe.edu.pucp.perugopf.presentation.adapters.EventAdapter;
import pe.edu.pucp.perugopf.presentation.utils.AdapterClickListener;

public class MainActivity extends BaseActivity implements IMainContract.IView {

    private TextView textViewResult;
    private RecyclerView recyclerViewEvents;
    private ProgressBar progressBarMain;
    private EventAdapter eventAdapter;
    private List<Event> eventList;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        presenter.attachView(this);
        textViewResult = findViewById(R.id.tv_no_events);
        progressBarMain = findViewById(R.id.progressBarMain);
        recyclerViewEvents = findViewById(R.id.recyclerViewEvents);
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(eventList);
        eventAdapter.setOnItemClickListener(new AdapterClickListener() {
            @Override
            public void onClick(int position) {
                gotToDetailEvent(eventList.get(position).getId());
            }
        });
        recyclerViewEvents.setAdapter(eventAdapter);
        presenter.getAllEvent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
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
    public void getAllEventSuccess(List<Event> eventList) {
        this.eventList.addAll(eventList);
        eventAdapter.notifyDataSetChanged();
    }

    @Override
    public void gotToDetailEvent(int eventId) {
        Intent intent = new Intent(this, EventDetailActivity.class);
        intent.putExtra("event_id", eventId);
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

package pe.edu.pucp.perugopf.presentation.activities.event_detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.R;
import pe.edu.pucp.perugopf.base.BaseActivity;
import pe.edu.pucp.perugopf.data.entities.EventDetail;
import pe.edu.pucp.perugopf.di.components.DaggerPresentationComponent;
import pe.edu.pucp.perugopf.di.modules.PresentationModule;
import pe.edu.pucp.perugopf.presentation.adapters.CommentAdapter;

public class EventDetailActivity extends BaseActivity
        implements IEventDetailContract.IView {

    @Inject
    EventPresenter presenter;
    TextView idTextView;
    TextView userIdTextView;
    TextView titleTextView;
    TextView bodyTextView;
    RecyclerView rvComments;
    ProgressBar progressBar;

    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_event_detail;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        idTextView = findViewById(R.id.idTextView);
        userIdTextView = findViewById(R.id.userIdTextView);
        titleTextView = findViewById(R.id.titleTextView);
        bodyTextView = findViewById(R.id.bodyTextView);
        progressBar = findViewById(R.id.progressBar);
        rvComments = findViewById(R.id.rv_comments);
        int id = getIntent().getIntExtra("event_id", -1);
        if(id == -1){
            showError("No Nos llego el eventId");
            finish();
        }
        commentAdapter = new CommentAdapter();
        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(commentAdapter);
        presenter.attachView(this);
        presenter.getEvent(id);
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
    public void showError(String errorMsg) {
        Toast.makeText(this,errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void getEventDetailSuccess(EventDetail eventDetail) {
        idTextView.setText(String.valueOf(eventDetail.event.getId()));
        userIdTextView.setText(String.valueOf(eventDetail.event.getUserId()));
        titleTextView.setText(eventDetail.event.getTitle());
        bodyTextView.setText(eventDetail.event.getText());
        commentAdapter.setCommentList(eventDetail.comments);
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.detachView();
    }
}

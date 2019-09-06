package emerge.project.mrsolution_admin_micro.ui.adapters.location;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.mrsolution_admin_micro.R;
import emerge.project.mrsolution_admin_micro.ui.activity.locations.LocationPresenter;
import emerge.project.mrsolution_admin_micro.ui.activity.locations.LocationPresenterImpli;
import emerge.project.mrsolution_admin_micro.ui.activity.locations.LocationView;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class LocationApprovedAdapter extends RecyclerView.Adapter<LocationApprovedAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<LocationEntitie> locationEntitieItems;

    int globalePotition;
    int count = 0;

    LocationPresenter locationPresenter;

    public LocationApprovedAdapter(Context mContext, ArrayList<LocationEntitie> item, LocationView locationview) {
        this.mContext = mContext;
        this.locationEntitieItems = item;
        locationPresenter = new LocationPresenterImpli(locationview);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_locations_approved, parent, false);
        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final LocationEntitie LocationEntitie = locationEntitieItems.get(position);

        globalePotition = position;


        holder.textviewRep.setText(LocationEntitie.getCreatedByName());
        holder.textviewLocation.setText(LocationEntitie.getName());
        holder.textviewAddress.setText(LocationEntitie.getAddress());


        holder.relativeLayourMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationPresenter.getSelectedLocationApproved(LocationEntitie);
            }
        });




    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {

    }

    @Override
    public int getItemCount() {
        return locationEntitieItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

        @BindView(R.id.mapView)
        MapView mapView;

        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayourMain;

        @BindView(R.id.textview_rep)
        TextView textviewRep;

        @BindView(R.id.textview_location)
        TextView textviewLocation;

        @BindView(R.id.textview_address)
        TextView textviewAddress;


        GoogleMap mapCurrent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if (mapView != null) {
                mapView.onCreate(null);
                mapView.onResume();
                mapView.getMapAsync(this);

            }


        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(mContext);
            mapCurrent = googleMap;
            LatLng sydney;

            mapCurrent.getUiSettings().setMyLocationButtonEnabled(true);
            boolean success = mapCurrent.setMapStyle(MapStyleOptions.loadRawResourceStyle(mContext, R.raw.map));

            if (count == 0) {
                sydney = new LatLng(locationEntitieItems.get(0).getLatitude(), locationEntitieItems.get(0).getLongitude());
            } else {
                sydney = new LatLng(locationEntitieItems.get(globalePotition).getLatitude(), locationEntitieItems.get(globalePotition).getLongitude());
            }
            count++;
            mapCurrent.addMarker(new MarkerOptions().position(sydney).title(""));
            mapCurrent.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));


        }
    }


}

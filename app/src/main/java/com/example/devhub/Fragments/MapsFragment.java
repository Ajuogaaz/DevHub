package com.example.devhub.Fragments;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.devhub.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class MapsFragment extends Fragment {


    List<ParseUser> users;
    private static String TAG = MapsFragment.class.getSimpleName();
    private Bitmap bmp;
    private Bitmap resizedBitmap;
    BitmapDescriptor defaultMarker;



    private OnMapReadyCallback callback = googleMap -> {

        LatLng currentUser = new LatLng(ParseUser.getCurrentUser().getNumber("Latitude").doubleValue(),
                ParseUser.getCurrentUser().getNumber("Longitude").doubleValue());
        googleMap.addMarker(new MarkerOptions().position(currentUser).title(ParseUser.getCurrentUser().getString("PreferredName")));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(currentUser));

        PopulateAllUsers(googleMap);

    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }


    }

    private void PopulateAllUsers(GoogleMap googleMap) {

        users = new ArrayList<>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.findInBackground((userlist, e) -> {
            if (e == null) {
                users.addAll(userlist);

                inititiatePlotting(googleMap);

            } else {
                Log.e(TAG, "Error"+ e.getMessage());
            }
        });

    }

    private void inititiatePlotting(GoogleMap googleMap) {




        for(ParseUser user : users){

            if(!user.getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {

                View marker = ((LayoutInflater) requireContext().getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_map, null);

                CircleImageView markerImage = marker.findViewById(R.id.user_dp);

                String ImageUrl = "";

                if(user.getBoolean("HasUploadedPic")){


                    ParseFile image = user.getParseFile("ProfilePic");
                    image.getDataInBackground((data, e) -> {
                        if (e == null) {
                            // Decode the Byte[] into

                            bmp = BitmapFactory
                                    .decodeByteArray(
                                            data, 0,
                                            data.length);
                            float aspectRatio = bmp.getWidth() / (float) bmp.getHeight();
                            int newWidth = 120;
                            int newHeight = Math.round(newWidth / aspectRatio);
                            resizedBitmap = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);
                            resizedBitmap = getBitmapRoundedCorners(resizedBitmap);

                        }
                        defaultMarker = BitmapDescriptorFactory.fromBitmap(resizedBitmap);

                        // listingPosition is a LatLng
                        LatLng listingPosition = new LatLng(user.getNumber("Latitude").doubleValue()
                                , user.getNumber("Longitude").doubleValue());
                        // Create the marker on the fragment
                        googleMap.addMarker(new MarkerOptions()
                                .position(listingPosition)
                                .title(user.getString("PreferredName"))
                                .snippet(user.getString("Bio"))
                                .icon(defaultMarker));
                    });


                }else{
                    ImageUrl = user.getString("githubProfilePic");
                }

                if(!ImageUrl.isEmpty()) {
                    Glide.with(requireContext())
                            .load(ImageUrl)
                            .into(markerImage);
                }

                DisplayMetrics displayMetrics = new DisplayMetrics();

                requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
                marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
                marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
                marker.buildDrawingCache();
                Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                marker.draw(canvas);


                defaultMarker = BitmapDescriptorFactory.fromBitmap(bitmap);

                // listingPosition is a LatLng
                LatLng listingPosition = new LatLng(user.getNumber("Latitude").doubleValue()
                        , user.getNumber("Longitude").doubleValue());
                // Create the marker on the fragment
                googleMap.addMarker(new MarkerOptions()
                        .position(listingPosition)
                        .title(user.getString("PreferredName"))
                        .snippet(user.getString("Bio"))
                        .icon(defaultMarker));
            }
        }

    }

    public Bitmap createCustomMarker(Context context, ParseUser user) {

        View marker = ((LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_map, null);

        CircleImageView markerImage = marker.findViewById(R.id.user_dp);

        String ImageUrl = "";

        if(user.getBoolean("HasUploadedPic")){


            ParseFile image = user.getParseFile("ProfilePic");
            image.getDataInBackground((data, e) -> {
                if (e == null) {
                    // Decode the Byte[] into

                    bmp = BitmapFactory
                            .decodeByteArray(
                                    data, 0,
                                    data.length);
                    float aspectRatio = bmp.getWidth() / (float) bmp.getHeight();
                    int newWidth = 120;
                    int newHeight = Math.round(newWidth / aspectRatio);
                    resizedBitmap = Bitmap.createScaledBitmap(bmp, newWidth, newHeight, false);
                    resizedBitmap = getBitmapRoundedCorners(resizedBitmap);

                }
            });


        }else{
            ImageUrl = user.getString("githubProfilePic");
        }

        if(!ImageUrl.isEmpty()) {
            Glide.with(context)
                    .load(ImageUrl)
                    .into(markerImage);
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

    private Bitmap getBitmapRoundedCorners(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(resizedBitmap.getWidth(),
                resizedBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, resizedBitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;

    }

}
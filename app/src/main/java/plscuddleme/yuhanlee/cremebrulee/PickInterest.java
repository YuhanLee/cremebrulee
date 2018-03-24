package plscuddleme.yuhanlee.cremebrulee;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PickInterest extends AppCompatActivity {
    private List<String> interestImageIds;
    private Context context = PickInterest.this;
    private ImageView displayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_interest);

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://creme-brulee-1111.appspot.com/dogs");
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a reference with an initial file path and name
        StorageReference pathReference = storageRef.child("dog-1.jpg");
        StorageReference islandRef = storageRef.child("images/island.jpg");

        String internetUrl = "http://www.zarias.com/wp-content/uploads/2015/12/61-cute-puppies.jpg";
        //we pass context because glide knows when the activity is gone and can get rid of the image
//        Glide
//                .with(context)
//                .load(internetUrl)
//                .into(displayImage);
//

        // ImageView in your Activity




    }
}

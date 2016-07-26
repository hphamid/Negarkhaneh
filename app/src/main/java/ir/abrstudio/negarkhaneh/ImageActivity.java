package ir.abrstudio.negarkhaneh;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageActivity extends Activity {

    private ImageView backgroundImage;
    private ImageButton like;
    private ImageButton share;
    private ImageButton wallpaper;
    private ImageButton download;
    private TextView title;
    private Image image;

    private class Download implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "negar-" + image.getImageTitle() + ".jpg");
            Bitmap image = ((BitmapDrawable)backgroundImage.getDrawable()).getBitmap();
            compressAndSaveImage(f, image);
            addImageToGallery(v.getContext().getContentResolver(),"jpg", f);
            Toast.makeText(v.getContext(), "Saved to Gallery", Toast.LENGTH_SHORT).show();
        }

    }


    private class Share implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "sharing...", Toast.LENGTH_SHORT).show();
            Bitmap image = ((BitmapDrawable)backgroundImage.getDrawable()).getBitmap();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
            try {
                if(f.createNewFile()) {
                    compressAndSaveImage(f, image);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + Environment.getExternalStorageDirectory() +
                    File.separator + "temporary_file.jpg"));
            startActivity(Intent.createChooser(share, "Share Image"));
        }

    }

    private class Like implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(like.getTag() != null){
                like.setImageResource(R.drawable.like_button);
                like.setTag(null);
            }else{
                like.setImageResource(R.drawable.like_button_p);
                like.setTag(new Object());
            }
        }

    }

    private class Wallpaper implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
            Bitmap image = ((BitmapDrawable)backgroundImage.getDrawable()).getBitmap();
            try {
                wm.setBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean compressAndSaveImage(File file, Bitmap bitmap) {
        boolean result = false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            result = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Uri addImageToGallery(ContentResolver cr, String imgType, File filepath) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "player");
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "player");
        values.put(MediaStore.Images.Media.DESCRIPTION, "");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/" + imgType);
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATA, filepath.toString());

        return cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        this.image = (Image) getIntent().getExtras().get("image");
        findViewItems();
        setListeners();
        setContents();
    }

    private void findViewItems() {
        backgroundImage = (ImageView) findViewById(R.id.main_image);
        like = (ImageButton) findViewById(R.id.star);
        share = (ImageButton) findViewById(R.id.share);
        wallpaper = (ImageButton) findViewById(R.id.wallpaper);
        download = (ImageButton) findViewById(R.id.download);
        title = (TextView) findViewById(R.id.title);
    }

    private void setListeners() {
        like.setOnClickListener(new Like());
        share.setOnClickListener(new Share());
        wallpaper.setOnClickListener(new Wallpaper());
        download.setOnClickListener(new Download());
    }

    private void setContents() {
        title.setText(image.getImageTitle());
        Glide.with(this).load(image.getImageAddress()).asBitmap().centerCrop().into(backgroundImage);
    }

}

package com.ronlu.ratemyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.android.material.button.MaterialButton;
public class RateMyApp {
    private static int selectedStar = -1;

    /**
     Displays a custom dialog with a star rating system and two buttons to prompt users to rate an app. If the user
     rates the app 4 or 5 stars, they are directed to the app's page on the market. If the rating is below 3 stars,
     a separate feedback dialog is shown to the user.
     @param activity The activity used to get the LayoutInflater for the custom dialog.
     @param context The context used to create the AlertDialog.Builder.
     @param imageID The resource ID of the app's icon image.
     @param feedbackListener The FeedbackReceivedListener used to listen for feedback from the user.
     @throws NullPointerException if either activity, context, or feedbackListener are null.
     @see FeedbackReceivedListener
     */
    public static void reteMeOnMarketDialog(Activity activity, Context context,int imageID , final FeedbackReceivedListener feedbackListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View customDialog = inflater.inflate(R.layout.ratemyapp_dialog, null);
        builder.setView(customDialog);
        AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(false);

        //TODO continue from here -->
        final AppCompatImageButton dialog_BTN_star0 = customDialog.findViewById(R.id.dialog_BTN_star0);
        final AppCompatImageButton dialog_BTN_star1 = customDialog.findViewById(R.id.dialog_BTN_star1);
        final AppCompatImageButton dialog_BTN_star2 = customDialog.findViewById(R.id.dialog_BTN_star2);
        final AppCompatImageButton dialog_BTN_star3 = customDialog.findViewById(R.id.dialog_BTN_star3);
        final AppCompatImageButton dialog_BTN_star4 = customDialog.findViewById(R.id.dialog_BTN_star4);
        final MaterialButton dialog_BTN_cancel = customDialog.findViewById(R.id.dialog_BTN_cancel);
        final MaterialButton dialog_BTN_submit = customDialog.findViewById(R.id.dialog_BTN_submit);

        final AppCompatImageView dialog_IMG_appIcon = customDialog.findViewById(R.id.dialog_IMG_appIcon);
        dialog_IMG_appIcon.setImageResource(imageID);

        final AppCompatImageButton[] stars = new AppCompatImageButton[]{dialog_BTN_star0, dialog_BTN_star1, dialog_BTN_star2, dialog_BTN_star3, dialog_BTN_star4};
        dialog_BTN_submit.setEnabled(false);
        final int drawableActive = R.drawable.ic_star;
        final int drawableDeactivate = R.drawable.ic_empty_star;

        View.OnClickListener starsClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedIndex = -1;
                for (int i = 0; i < stars.length; i++) {
                    if (stars[i].getId() == v.getId()) {
                        clickedIndex = i;
                        break;
                    }
                }

                if (clickedIndex != -1) {
                    for (int i = 0; i <= clickedIndex; i++) {
                        stars[i].setImageResource(drawableActive);
                    }
                    for (int i = clickedIndex + 1; i < stars.length; i++) {
                        stars[i].setImageResource(drawableDeactivate);
                    }
                }

                dialog_BTN_submit.setEnabled(true);
                selectedStar = clickedIndex;
                Toast.makeText(activity, "selectedStar = " +clickedIndex, Toast.LENGTH_SHORT).show();
            }
        };

        dialog_BTN_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });

        dialog_BTN_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedStar<3)
                    showFeedbackDialog(activity, context, feedbackListener);
                else if(selectedStar == 4)
                    showRateOnMarketDialog(activity);
                alert.cancel();
            }
        });
        dialog_BTN_star0.setOnClickListener(starsClickListener);
        dialog_BTN_star1.setOnClickListener(starsClickListener);
        dialog_BTN_star2.setOnClickListener(starsClickListener);
        dialog_BTN_star3.setOnClickListener(starsClickListener);
        dialog_BTN_star4.setOnClickListener(starsClickListener);
        alert.show();

    }

    private static void showRateOnMarketDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Thank you!");
        builder.setIcon(R.drawable.ic_market);
        builder.setMessage("Would you like to post your review on app store. This will help and motivate us a lot.");
        builder.setCancelable(false);

        builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openAppPageOnMarket(activity);
            }
        });
        builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
        Toast.makeText(activity, "market", Toast.LENGTH_SHORT).show();
    }

    private static void showFeedbackDialog(Activity activity, Context context, FeedbackReceivedListener feedbackListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("We're Really Sorry");
        builder.setMessage("Could you tell us what problem you faced. This will help us improve.");
        final EditText input = new EditText(activity);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Submit", null);
        builder.setNegativeButton("No Thanks!", (dialog, which) -> {
            Toast.makeText(context, "Maybe next time!", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String inputString = input.getText().toString();
            if (inputString.trim().isEmpty()) {
                Toast.makeText(context, "Cannot Send an Empty feedback", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Thank you for your feedback", Toast.LENGTH_SHORT).show();
                feedbackListener.onFeedbackReceived(inputString);
                dialog.dismiss();
            }
        });
    }

    private static void openAppPageOnMarket(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            activity.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, " unable to find google play app", Toast.LENGTH_LONG).show();
        }
    }

    public interface FeedbackReceivedListener {
        void onFeedbackReceived(String feedback);
    }

}

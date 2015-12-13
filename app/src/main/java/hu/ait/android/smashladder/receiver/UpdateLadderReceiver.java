package hu.ait.android.smashladder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import hu.ait.android.smashladder.service.UpdateService;

public class UpdateLadderReceiver extends BroadcastReceiver {
    public UpdateLadderReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service1 = new Intent(context, UpdateService.class);
        context.startService(service1);
    }
}

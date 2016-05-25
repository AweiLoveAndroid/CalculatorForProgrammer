package com.example.calculatorforprogrammer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaomi.market.sdk.UpdateResponse;
import com.xiaomi.market.sdk.UpdateStatus;
import com.xiaomi.market.sdk.XiaomiUpdateAgent;
import com.xiaomi.market.sdk.XiaomiUpdateListener;


public class AboutAppActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button checkNewButton = (Button)findViewById(R.id.check_view_button);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        TextView versionName = (TextView)findViewById(R.id.versionNameTextView);
        TextView appName = (TextView)findViewById(R.id.appNameTextView);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.about_developer_title_bacground);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.blue_bright));
        try {
            versionName.setText("V " + getVersionName());
        }catch (Exception e){
        }
        appName.setText(getResources().getString(R.string.app_name));

    }

    private String getVersionName() throws Exception
    {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
        String version = packInfo.versionName;
        return version;
    }

    public void onClickCheckNewButton(View view){
        //Toast toast = new Toast(this);
        Toast.makeText(this,getResources().getString(R.string.checking_new),Toast.LENGTH_SHORT).show();
        XiaomiUpdateAgent.setUpdateAutoPopup(false);
        XiaomiUpdateAgent.setUpdateListener(new XiaomiUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.STATUS_UPDATE:
                        // �и��£� UpdateResponseΪ���θ��µ���ϸ��Ϣ
                        // ���а���������Ϣ�����ص�ַ��MD5У����Ϣ�ȣ������д������ذ�װ
                        // ���ϣ�� SDK�����ӹ����ذ�װ���ˣ��ɵ���
                        AlertDialog alert = new AlertDialog.Builder(AboutAppActivity.this).create();
                        alert.setIcon(R.drawable.icon_30);
                        alert.setTitle(getResources().getString(R.string.app_name));
                        alert.setMessage(getResources().getString(R.string.checked_new_alert_message));
                        alert.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.alert_update_yes_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                XiaomiUpdateAgent.arrange();
                            }
                        });
                        alert.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alert.show();
                        break;
                    case UpdateStatus.STATUS_NO_UPDATE:
                        // �޸��£� UpdateResponseΪnull
                        Toast.makeText(AboutAppActivity.this,getResources().getString(R.string.checked_updated),Toast.LENGTH_LONG).show();
                        break;
                    case UpdateStatus.STATUS_NO_WIFI:
                        // ������ֻ��WiFi�¸��£���WiFi������ʱ�� UpdateResponseΪnull
                        break;
                    case UpdateStatus.STATUS_NO_NET:
                        // û�����磬 UpdateResponseΪnull
                        Toast.makeText(AboutAppActivity.this,getResources().getString(R.string.checked_no_network),Toast.LENGTH_LONG).show();
                        break;
                    case UpdateStatus.STATUS_FAILED:
                        // �������������ͨѶʧ�ܣ����Ժ����ԣ� UpdateResponseΪnull
                        Toast.makeText(AboutAppActivity.this,getResources().getString(R.string.checked_no_server),Toast.LENGTH_LONG).show();
                        break;
                    case UpdateStatus.STATUS_LOCAL_APP_FAILED:
                        // �����»�ȡ���ذ�װӦ����Ϣʧ�ܣ� UpdateResponseΪnull
                        Toast.makeText(AboutAppActivity.this,getResources().getString(R.string.checked_error),Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        });
        XiaomiUpdateAgent.update(this);
    }
}

package com.sm.usagesexample;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rz Rasel on 2018-01-23.
 */

public class DeviceIPApi {
    private Context context;
    private EventListenerHandler eventListenerHandler;

    public DeviceIPApi(Context argContext) {
        context = argContext;
    }

    public String getInterfaceIPAddress() {
        NetworkInterfaceIp networkInterfaceIp = new NetworkInterfaceIp();
        String actualIPAddress = null;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo.isConnected()) {
                actualIPAddress = networkInterfaceIp.getWifiIP();
            }
        }
        if (actualIPAddress == null) {
            actualIPAddress = networkInterfaceIp.getNetworkInterfaceIpAddress();
        } else {
            if (actualIPAddress.isEmpty()) {
                actualIPAddress = "127.0.0.1";
            }
        }
        return actualIPAddress;
    }

    private class NetworkInterfaceIp {
        private String getWifiIP() {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null && wifiManager.isWifiEnabled()) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
                return ip;
            }
            return null;
        }

        private String getNetworkInterfaceIpAddress() {
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface networkInterface = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                            String host = inetAddress.getHostAddress();
                            return host;
                        }
                    }

                }
            } catch (Exception ex) {
                Log.e("IP Address", "getLocalIpAddress", ex);
            }
            return null;
        }
    }

    public String getApparentIPAddress(EventListenerHandler argEventListenerHandler) {
        String actualIPAddress = null;
        eventListenerHandler = argEventListenerHandler;
        new AsyncLoadSeamingIP().execute("http://www.ip-api.com/json");
        return actualIPAddress;
    }

    private class AsyncLoadSeamingIP extends AsyncTask<String, Void, HashMap<String, String>> {
        HashMap<String, String> hashMapIPDetails = new HashMap<String, String>();
        public AsyncLoadSeamingIP()
        {
            hashMapIPDetails.put("country", "");
            hashMapIPDetails.put("city", "");
            hashMapIPDetails.put("countryCode", "");
            hashMapIPDetails.put("latitude", "");
            hashMapIPDetails.put("longitude", "");
            hashMapIPDetails.put("ip", "");
            hashMapIPDetails.put("regionName", "");
            hashMapIPDetails.put("timezone", "");
            hashMapIPDetails.put("zip", "");
            hashMapIPDetails.put("status", "");
        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected HashMap<String, String> doInBackground(String... argParams) {
            String hostUrl = argParams[0];
            //System.out.println("HOST_URL: " + hostUrl);
            String retVal = null;
            try {
                URL url = new URL(hostUrl);
                InputStream inputStream = url.openStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                inputStream.close();
                retVal = stringBuilder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonRoot = new JSONObject(retVal);
                System.out.println("COUNTRY: " + jsonRoot.getString("country"));
                hashMapIPDetails.put("country", jsonRoot.getString("country"));
                hashMapIPDetails.put("city", jsonRoot.getString("city"));
                hashMapIPDetails.put("countryCode", jsonRoot.getString("countryCode"));
                hashMapIPDetails.put("latitude", jsonRoot.getString("lat"));
                hashMapIPDetails.put("longitude", jsonRoot.getString("lon"));
                hashMapIPDetails.put("ip", jsonRoot.getString("query"));
                hashMapIPDetails.put("regionName", jsonRoot.getString("regionName"));
                hashMapIPDetails.put("timezone", jsonRoot.getString("timezone"));
                hashMapIPDetails.put("zip", jsonRoot.getString("zip"));
                hashMapIPDetails.put("status", jsonRoot.getString("status"));
            } catch (JSONException e) {
                System.out.println(e);
            }
            return hashMapIPDetails;
        }

        @Override
        protected void onPostExecute(HashMap<String, String> argResult) {
            //System.out.println("onPostExecute: " + argResult);
            if (eventListenerHandler != null) {
                eventListenerHandler.onPostExecute(argResult);
            }
        }
    }

    public interface EventListenerHandler {
        public void onPostExecute(HashMap<String, String> argResult);
    }
    //http://www.ip-api.com/json
    //http://www.itcuties.com/java/read-url-to-string/
}
/*
getIPAddress == php ip address

*/

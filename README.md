# HeThongGoiMonAnTrongNhaHang

## Account

```
$ Tài khoản giao diện phục vụ: phucvu/phucvu
```

```
& Tài khoản giao diện nhà bếp: nhabep/nhabep
```

```
& Tài khoản giao diện thu ngân: thungan/thungan
```

```
& Tài khoản giao diện quản lý: quanly/quanly
```
## Firebase

```
$ user: nienluan3cntt9 pass: nienluan3cntt90123)!@#
```
## Screenshots
|<h1 align="center">Choose Table</h1>|<h1 align="center">Choose Food</h1>|
|--------|--------|
|![a](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/chonBan.png)|![b](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/chonMon.png)|

|<h1 align="center">Choose Dish Group</h1>|<h1 align="center">Log In</h1>|
|--------|--------|
|![a](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/chonNhomMon.png)|![b](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/dangNhap.png)|

|<h1 align="center">Kitchen Logout</h1>|<h1 align="center">Service Logout</h1>|
|--------|--------|
|![a](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/dangXuatFormNhaBep.png)|![b](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/dangXuatFormPhucVu.png)|

|<h1 align="center">Cashier Sign Out</h1>|<h1 align="center">Management Directory</h1>|
|--------|--------|
|![a](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/dangXuatFormThuNgan.png)|![b](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/danhMucQuanLy.png)|

|<h1 align="center">Bill</h1>|<h1 align="center">When Clicking The Pay Button</h1>|
|--------|--------|
|![a](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/hoaDonThanhToan.png)|![b](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/khiNhanNutThanhToan.png)|

|<h1 align="center">Kitchen</h1>|<h1 align="center">Manage</h1>|
|--------|--------|
|![a](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/nhaBep.png)|![b](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/quanLy.png)|

|<h1 align="center">Kitchen Manager</h1>|<h1 align="center">Cashier</h1>|
|--------|--------|
|![a](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/quanLyBanAn.png)|![b](https://raw.githubusercontent.com/dev-khanh/HeThongGoiMonAnTrongNhaHang/master/thiet_ke/thietke/thuNgan.png)|


| Key                             | Description                                                                                                                                                                 | Example                                                                                  |
| ------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| IOS_APP_IDENTIFIER              | iOS app bundler                                                                                                                                                             | com.futureassembly.techsauce                                                             |
| IOS_APP_DISPLAY_NAME            | iOS app display name                                                                                                                                                        | Techsauce                                                                                |
| TENANTS_URL                     | api endpoint to get tenant info                                                                                                                                             | https://staging.delegateconnect.co/api/v1/tenant/1                                       |
| PUSHER_BEAM_INSTANCE_ID         | Pusher Beam 's instance id                                                                                                                                                  |                                                                                          |
| ROLLBAR_POST_CLIENT_ITEM        | Rollbar 's post client                                                                                                                                                      |                                                                                          |
| DOMAIN_URL                      | Domain url                                                                                                                                                                  | https://staging.delegateconnect.co/                                                      |
| API_URL                         | BaseURl                                                                                                                                                                     | https://staging.delegateconnect.co/api/v1/                                               |
| MAPBOX_PUBLIC_KEY               | Mapbox public key (can be re-use for all app)                                                                                                                               | pk.eyJ1IjoidHJ1b25nIiwiYSI6ImNpd29kMnpsMjAwMG0yem1xYXU0cmpyaGUifQ.45xw9mg2P9uONRPeMP0viA |
| MAPBOX_STYLE                    | Mapbox style(can be re-use for all app)                                                                                                                                     | mapbox://styles/truong/ck4wj20lp1o4h1co9upom3ikx                                         |
| MAPWIZE_API_KEY                 | Mapwize api key                                                                                                                                                             | 24ed2f0eeedbc1316409b1e31dfd5050                                                         |
| ANDROID_GOOGLE_SERVICE_JSON     | google-services.json encoded to base64 </br> Use to push notification android https://support.google.com/firebase/answer/7015592#)                                          | `\$ openssl base64 -A -in google-services.json                                           | pbcopy` </br> Need 1 each app |
| ANDROID_RELEASE_SERVICE_ACCOUNT | GoogleAPIServiceAccountUser.json encoded to base64 </br> Support release android https://docs.fastlane.tools/getting-started/android/setup/#collect-your-google-credentials | `\$ openssl base64 -A -in GoogleAPIServiceAccountUser.json                               | pbcopy` |
| ANDROID_PACKAGE_NAME            | Android package name for app                                                                                                                                                | com.iconiclive.dc_racs_asc                                                               |
| ANDROID_APP_NAME                | Name of android app                                                                                                                                                         | Techsauce                                                                                |

2. Add new jobs to release new app on circleCI/config.yml using new context

3. Create a new application on appstore connect and google play console.
   Note: (Android) You have to create the application, upload keystore on app signing tab and upload app.aab once through the Play Console before using Fastlane. app.aab can be found on circleCI artifact

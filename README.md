# NewPermissions
Android 6.0权限新特性项目
>## 新权限使用实例 ##
#### 新增API ####
- ContextCompact.checkSelfPermission()
	> 检测app是否拥有此权限
- ActivityCompat.requestPermissions()
	> 申请某个权限
- onRequestPermissionsResult()
	> 申请权限的回调，处理申请权限成功失败
- ActivityCompat.shouldShowRequestPermissionRationale
	> 用于给用户解释申请改权限是用于做什么的。用户拒绝我们的申请之后才会出现

#### 使用流程 ###
- 在AndroidManifest中添加需要的权限（不可省）
- 检查权限

    	shell命令：
    	adb shell pm list permissions -d -g

- 申请授权
- 处理申请回调

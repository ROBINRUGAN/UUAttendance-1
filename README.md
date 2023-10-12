# UU考勤安卓客户端项目文档

## 项目功能介绍

- UU考勤，通过数据上云处理，结合手机的便携性、易用性，实现即时定点签到、课表课程查看、请销假管理、点名督导等功能。

## 技术栈与项目亮点

- 使用安卓原生技术开发，稳定性强、效率高、适配范围广，隐私权限要求少；
- 创新卡片滑动交互方式实现督导操作；
- 依托高德地图定位技术实现用户定位、地图查看功能；
- 采用MVVM技术架构实现用户界面和业务逻辑分离；
- 借助WebSocket长连接技术提升信息触达率，确保点名时信息的即时传递；
- 依托Glide实现图片形态信息传输；
- 依托Retrofit2以及二层封装，大幅简化了网络操作；
- 依托MMKV实现高性能和安全性的数据存储；
- 通过BaseActivity、BaseFragment等封装，增加代码复用率。

## 目录结构介绍

WorkingDirection/
├────.gitignore
├────build.gradle
├────libs/
├────proguard-rules.pro
├────src/
│    ├────androidTest/
│    │    └────java/
│    │    │    └────com/
│    │    │    │    └────uu/
│    │    │    │    │    └────attendance/
│    │    │    │    │    │    └────ExampleInstrumentedTest.kt
│    ├────main/
│    │    ├────AndroidManifest.xml
│    │    ├────java/
│    │    │    └────com/
│    │    │    │    └────uu/
│    │    │    │    │    └────attendance/
│    │    │    │    │    │    ├────base/											        		抽象基类
│    │    │    │    │    │    │    ├────dto/
│    │    │    │    │    │    │    └────ui/
│    │    │    │    │    │    ├────model/
│    │    │    │    │    │    │    ├────Constants.kt								    			常量
│    │    │    │    │    │    │    └────network/									    			网络请求相关
│    │    │    │    │    │    │    │    ├────api/												    请求接口封装
│    │    │    │    │    │    │    │    ├────dto/											    	数据类
│    │    │    │    │    │    │    │    └────service/									    		请求服务
│    │    │    │    │    │    ├────MyApplication.kt
│    │    │    │    │    │    ├────ui/													        	界面相关
│    │    │    │    │    │    │    ├────common/											           	通用组件
│    │    │    │    │    │    │    │    └────view/
│    │    │    │    │    │    │    │    │    └────RoundImageView.kt							    	自实现圆形图片
│    │    │    │    │    │    │    ├────coursetable/										    	课程表tab部分
│    │    │    │    │    │    │    │    ├────activity/
│    │    │    │    │    │    │    │    │    └────NewLeaveApplicationActivity.kt					请假
│    │    │    │    │    │    │    │    ├────adapter/
│    │    │    │    │    │    │    │    │    └────CourseTableAdapter.kt						    	课程表适应器
│    │    │    │    │    │    │    │    ├────fragment/
│    │    │    │    │    │    │    │    │    ├────CourseTableFragment.kt
│    │    │    │    │    │    │    │    │    └────CourseTableItemFragment.kt
│    │    │    │    │    │    │    │    └────view/
│    │    │    │    │    │    │    │    │    └────CourseDetailPopup.kt								课程弹窗
│    │    │    │    │    │    │    ├────login/												    	登录部分
│    │    │    │    │    │    │    │    └────activity/
│    │    │    │    │    │    │    │    │    └────LoginActivity.kt
│    │    │    │    │    │    │    ├────main/													    主界面部分
│    │    │    │    │    │    │    │    ├────activity/
│    │    │    │    │    │    │    │    │    └────MainActivity.kt
│    │    │    │    │    │    │    │    └────viewmodel/
│    │    │    │    │    │    │    │    │    └────MainViewModel.kt								    主界面公用数据存储
│    │    │    │    │    │    │    ├────me/													        我的tab部分
│    │    │    │    │    │    │    │    ├────activity/
│    │    │    │    │    │    │    │    │    ├────AttendanceAppealActivity.kt						考勤申诉查看
│    │    │    │    │    │    │    │    │    ├────ChangePwdActivity.kt							    修改密码
│    │    │    │    │    │    │    │    │    ├────LeaveApplicationActivity.kt						请假申请查看
│    │    │    │    │    │    │    │    │    └────RulesActivity.kt									考勤规则
│    │    │    │    │    │    │    │    ├────adapter/
│    │    │    │    │    │    │    │    │    ├────ApplicationListAdapter.kt
│    │    │    │    │    │    │    │    │    └────AttendanceAppealAdapter.kt
│    │    │    │    │    │    │    │    ├────fragment/
│    │    │    │    │    │    │    │    │    └────MeFragment.kt
│    │    │    │    │    │    │    │    └────view/
│    │    │    │    │    │    │    │    │    └────ItemFunctionEntryView.kt							功能项view
│    │    │    │    │    │    │    ├────signin/													    签到tab部分
│    │    │    │    │    │    │    │    ├────fragment/
│    │    │    │    │    │    │    │    │    └────SigninFragment.kt
│    │    │    │    │    │    │    │    ├────view/
│    │    │    │    │    │    │    │    │    └────MapContainerLayout.kt
│    │    │    │    │    │    │    │    └────viewmodel/
│    │    │    │    │    │    │    │    │    └────SigninViewModel.kt
│    │    │    │    │    │    │    └────supervise/												    督导tab部分
│    │    │    │    │    │    │    │    ├────activity/
│    │    │    │    │    │    │    │    │    └────SuperviseDetailActivity.kt						督导详情
│    │    │    │    │    │    │    │    ├────adapter/
│    │    │    │    │    │    │    │    │    ├────SuperviseCardAdapter.kt
│    │    │    │    │    │    │    │    │    ├────SuperviseListAdapter.kt
│    │    │    │    │    │    │    │    │    ├────SuperviseStudentDetailAdapter.kt
│    │    │    │    │    │    │    │    │    └────ViewPagerSuperviseDetailAdapter.kt
│    │    │    │    │    │    │    │    ├────fragment/
│    │    │    │    │    │    │    │    │    ├────SuperviseCardFragment.kt
│    │    │    │    │    │    │    │    │    ├────SuperviseFragment.kt
│    │    │    │    │    │    │    │    │    └────SuperviseListFragment.kt
│    │    │    │    │    │    │    │    ├────view/
│    │    │    │    │    │    │    │    │    └────cardswipelayout/									卡片交互实现
│    │    │    │    │    │    │    │    │    │    ├────CardConfig.kt
│    │    │    │    │    │    │    │    │    │    ├────CardItemTouchHelperCallback.kt
│    │    │    │    │    │    │    │    │    │    ├────CardLayoutManager.kt
│    │    │    │    │    │    │    │    │    │    └────OnSwipeListener.kt
│    │    │    │    │    │    │    │    └────viewmodel/
│    │    │    │    │    │    │    │    │    └────SuperviseViewModel.kt
│    │    │    │    │    │    └────util/															工具类
│    │    │    │    │    │    │    ├────ConnectionUtil.kt
│    │    │    │    │    │    │    ├────ConvertUtil.kt
│    │    │    │    │    │    │    ├────CoroutineUtil.kt
│    │    │    │    │    │    │    ├────CrashUtil.kt
│    │    │    │    │    │    │    ├────KeyboardUtil.kt
│    │    │    │    │    │    │    ├────KVUtil.kt
│    │    │    │    │    │    │    ├────LatLngUtil.kt
│    │    │    │    │    │    │    ├────LogUtil.kt
│    │    │    │    │    │    │    └────UriPathHelper.kt
│    │    └────res/                                                                                 布局与资源
│    │    │    ├────drawable/
│    │    │    │    ├────bg_btn_signin_nosign.xml
│    │    │    │    ├────bg_btn_signin_notsigned.xml
│    │    │    │    ├────bg_btn_signin_signed.xml
│    │    │    │    ├────bg_corner_10.xml
│    │    │    │    ├────bg_corner_20.xml
│    │    │    │    ├────bg_corner_35.xml
│    │    │    │    ├────bg_corner_bottom_35.xml
│    │    │    │    ├────bg_edittext.xml
│    │    │    │    ├────bg_edittext_checked.xml
│    │    │    │    ├────bg_edittext_unchecked.xml
│    │    │    │    ├────bg_login.webp
│    │    │    │    ├────bg_me_tv_identity.xml
│    │    │    │    ├────bg_page.xml
│    │    │    │    ├────bg_round_white.xml
│    │    │    │    ├────bg_supervise_texthint.xml
│    │    │    │    ├────ic_tab_coursetable.xml
│    │    │    │    ├────ic_tab_me.xml
│    │    │    │    ├────ic_tab_signin.xml
│    │    │    │    └────ic_tab_supervise.xml
│    │    │    ├────drawable-mdpi/
│    │    │    ├────drawable-xhdpi/
│    │    │    ├────drawable-xxhdpi/
│    │    │    ├────drawable-xxxhdpi/
│    │    │    ├────layout/
│    │    │    │    ├────activity_attendance_appeal.xml
│    │    │    │    ├────activity_change_pwd.xml
│    │    │    │    ├────activity_leave_application.xml
│    │    │    │    ├────activity_login.xml
│    │    │    │    ├────activity_main.xml
│    │    │    │    ├────activity_new_leave_application.xml
│    │    │    │    ├────activity_rules.xml
│    │    │    │    ├────activity_supervise_detail.xml
│    │    │    │    ├────fragment_coursetable.xml
│    │    │    │    ├────fragment_coursetable_item.xml
│    │    │    │    ├────fragment_me.xml
│    │    │    │    ├────fragment_signin.xml
│    │    │    │    ├────fragment_supervise.xml
│    │    │    │    ├────fragment_supervise_card.xml
│    │    │    │    ├────fragment_supervise_list.xml
│    │    │    │    ├────item_attendance_appeal.xml
│    │    │    │    ├────item_function_entry.xml
│    │    │    │    ├────item_leave_application.xml
│    │    │    │    ├────item_supervise_card.xml
│    │    │    │    ├────item_supervise_class.xml
│    │    │    │    ├────item_supervise_person.xml
│    │    │    │    └────popup_course_detail.xml
│    │    │    ├────menu/
│    │    │    │    └────bottom_nav_menu.xml
│    │    │    ├────mipmap-hdpi/
│    │    │    │    ├────ic_launcher.webp
│    │    │    │    └────ic_launcher_round.webp
│    │    │    ├────mipmap-mdpi/
│    │    │    │    ├────ic_launcher.webp
│    │    │    │    └────ic_launcher_round.webp
│    │    │    ├────mipmap-xhdpi/
│    │    │    │    ├────ic_launcher.webp
│    │    │    │    └────ic_launcher_round.webp
│    │    │    ├────mipmap-xxhdpi/
│    │    │    │    ├────ic_launcher.webp
│    │    │    │    └────ic_launcher_round.webp
│    │    │    ├────mipmap-xxxhdpi/
│    │    │    │    ├────ic_launcher.webp
│    │    │    │    └────ic_launcher_round.webp
│    │    │    ├────values/
│    │    │    │    ├────attr.xml
│    │    │    │    ├────colors.xml
│    │    │    │    ├────strings.xml
│    │    │    │    └────themes.xml
│    │    │    └────xml/
│    │    │    │    ├────backup_rules.xml
│    │    │    │    └────data_extraction_rules.xml
│    └────test/
│    │    └────java/
│    │    │    └────com/
│    │    │    │    └────uu/
│    │    │    │    │    └────attendance/
│    │    │    │    │    │    └────ExampleUnitTest.kt
└────tree.py

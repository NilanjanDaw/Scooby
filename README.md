# Scooby

<b>About:</b>
<br>
<p>
When fully developed Scooby will be a computer vision controlled object tracking robot along with speech recognition capabilities. It will ideally track the user's hand movements and or understand the commands spoken by the user and move accordingly.<br><br>
Scooby consists of mainly three parts: <br><br>
1.<b> The Vision part:</b> This part consists of a mobile application(android) which will track the user's hand movements and move the robot accordingly. For movements in the X-axis the bot will move left and right. For movements in the Y-axis the bot will tilt the servo motor(The mobile camera will be mounted on this servo motor) so as to keep the user's hand in the camera's focus.For movements in the Z-axis the bot will move backward and forward.<br>
2.<b> The Speech Recognition part:</b>	This part will also be handled by the mobile application. Google's speech recognition capabilities will be harnessed to processs natural language commands spoken by the user.<br>
3.<b> The Bot Hardware part:</b> The external stimuli processed by the mobile will be sent over to the bot over an USB OTG cable which will be understood by the bot and executed.<br><br>
</p>

<b> Project status: </b>
<br>
<p>
Scooby is currently in its phase 2 development stage. <br>
<b>Stage 1:</b> The speech recognition capabilities have been added. Though a button click is required to activate the speech recognition system. This aspect needs modification. Also the system was deployed as a separate application, which needs to be merged with the vision app.
<br>
<b>Stage 2 (in phase):</b>Basic Color blob tracking has been implemented. Though thresholding modification is required for better background subtraction. Also the color tracking is highly ineffective in tracking shadows.<br>
</p>

<b> Hardware and Software Requirements </b>
<br>
<p>
<b> Hardware Requirements: </b>
<br>
1.	Arduino Uno/ Mega(preferred) <br>
2.	Android version 5.0(api level 21) supported Mobile Phone with atleast 5MP camera.<br>
3.	USB OTG cable.<br>
4.	USB A to USB B cable.<br>
5.	Motors and Motor drivers.<br>
6.	Chassis, Mobile phone holder, Wheels, tank tracks, clamps, wires etc.<br>
<br>
<b> Software Requirements: </b>
<br>
1.	Android studio 1.4+ with android api SDK 23.<br>
2.	OpenCV 3.0.0<br>
3.	OpenCV Manager on the android device.<br>
4.	JDK 1.8+<br>
5.	Arduino IDE.<br>
</p>
<b> NB. For development purposes please create a separate branch and modify there. Merge with the master branch only if sure the code is working fine.</b>

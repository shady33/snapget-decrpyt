This is a folder that contains 3 ways of decrpyting snapchat images.
I am using snapchat ver 4.1.07 on my HTC One X.
To get snapchat images you need to have a rooted android device.
You can get instructions to root your device at xda-developers.
Once you receive a snap from somebody let the snap get downloaded but do not open it.
Then open any root browser on your android device and navigate to /data/data/com.snapchat.android/cache/received_image_snaps/ and copy the file with a .jpg.nomedia extension to your computer(you can use bluetooth or mail it to yourself).

Once you have the *.jpg.nomedia file you can use either use the python or ruby script based on what language you use.
For Python and Ruby:

Open the script in a text editor like gedit and add the input and output file paths and names for the file and run the script by typing "pyton snapget-python.py" or "ruby snapget-ruby.rb" and you will have the output file.

For android:
This is a widget. Download the contents of the folder. Build the apk file with eclipse and install it using adb to your device.
Then you add the widget to your homescreen.
Everytime you recieve a snap just press the button the file will get copied to "/sdcard/Snapget/".
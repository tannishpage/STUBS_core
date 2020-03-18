// Allows the user to interact with the settings file (sort of a wrapper for
// manipulating the settings file).
/*
Example settings file

[1] SERVERADDR:xxx.xxx.xxx.xxx
[2] PORT:5000
[3] KEEPBACKUPFILE:no
[4] AUTOBACKUP:no
[5] AUTOBACKUPFREQ:na
[6] DEVICENAME:MacBook_Air
[7] THINGSTOBACKUP:
/Users/tannishpage/Documents
/Users/tannishpage/Downloads
/Users/tannishpage/Pictures
/Users/tannishpage/Desktop
--------------------------------------------------------------------------------

Read the file until line 7 like A = B
Skip line 7 and everything after line 7 will be in a list of things to backup 
*/
#include <iostream>
#include <string.h>
#include <fstream>

using namespace std;

class Settings {
	private:
		string SETTINGS_FILE_NAME = "settings";
		string server_addr;
		int port;
		int send_file;
		int keep_backup_file;
		int auto_backup;
		int auto_backup_freq;
		string device_name;
		vector<string> folders_to_backup;


	Settings(){
		fstream settings_file;
		char * raw_settings;
		streampos size;
		settings_file.open(this->SETTINGS_FILE_NAME, ios::in);
		if (settings_file.is_open()){
			size = settings_file.tellg();
			raw_settings = new char [size+1];
			settings_file.seekg(0, ios::beg);
			settings_file.read(raw_settings, size);
			for (int i = 0; i <= size; i++){
				cout << raw_settings[i];
			}
		} else{
			cout << "Failed to load settings" << endl;
		}
		
	}

	// Write getters and setters for all the settings options
	// Then write a save function, the save function is essentially when all the 
	// feilds in the settings file are updated. 

};
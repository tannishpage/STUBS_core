/*
						LICENSE

This is the Settings interface, a component of STUBS.
Copyright (C) 2020  Tannishpage

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/


/* 
Allows the user to interact with the settings file (sort of a wrapper for 
manipulating the settings file).

Example settings file

[1] SERVERADDR:xxx.xxx.xxx.xxx TYPE STRING
[2] PORT:5000									 TYPE INT
[3] KEEPBACKUPFILE:no          TYPE INT
[4] AUTOBACKUP:no              TYPE INT
[5] AUTOBACKUPFREQ:0           TYPE INT
[6] DEVICENAME:MacBook_Air     TYPE STRING
[7] THINGSTOBACKUP:						 TYPE VECTOR<STRING>
/Users/tannishpage/Documents
/Users/tannishpage/Downloads
/Users/tannishpage/Pictures
/Users/tannishpage/Desktop
--------------------------------------------------------------------------------

Read the file until line 7 like A = B
Skip line 7 and everything after line 7 will be in a list of things to backup 
*/
#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <sstream>

using namespace std;

class Settings {
	private:
		string SETTINGS_FILE_NAME = "SETTINGS";
		string server_addr;
		int port;
		int send_file;
		int keep_backup_file;
		int auto_backup;
		int auto_backup_freq;
		string device_name;
		vector<string> folders_to_backup;
        int updated = 1;

        /**
		    Method will split up a given string at the character given. The default is 
		    \n.
		    
		    Parameters: 
			    - string to_split: the string that is to be splitted
			    - string splitter: the character/sub-string at with splitting is required
		    
		    Return:
			    - vector<string> splits: a vector (or List) of the sections of the string
															     that was split 
	     **/
	    vector<string> split(string to_split, string splitter = "\n") {
		    vector<string> splits;
		    int start = 0;
		    int end;
		    if (to_split.find(splitter) == to_split.npos){
			    splits.push_back(to_split);
			    return splits;
		    }
		    while ((end = to_split.find(splitter, start)) != to_split.npos){
			    splits.push_back(to_split.substr(start, end - start));
			    start = end + 1;
		    }
		    // Adds the remaining string to the splits vector
		    splits.push_back(to_split.substr(start));
	    
		    return splits;
        }

    public:
	    Settings(){
		    const int start_of_bkp_list = 6;
		    ifstream settings_file (SETTINGS_FILE_NAME, ios::in);
		    char * raw_settings;
		    streampos size;
		    if (settings_file.is_open()){
			    settings_file.seekg(0, ios::end);
			    size = settings_file.tellg();
			    raw_settings = new char [size];
			    settings_file.seekg(0, ios::beg);
			    settings_file.read(raw_settings, size);
			    string settings_data(raw_settings, size);
			    vector<string> settings_data_splitted = split(settings_data);
			    vector<string> sub_data;
			    for (int i = 0; i < settings_data_splitted.size(); i++){
				    if (i == 6){
					    continue;
				    }
				    if (i >= 7){
				      folders_to_backup.push_back(settings_data_splitted.at(i));
					    continue;
				    }
				    sub_data = split(settings_data_splitted.at(i), ":");
				    switch (i){
					    case 0:
						    server_addr = sub_data.at(1);
                            continue;
					    case 1:
						    port = stoi(string(sub_data.at(1)));
                            continue;
					    case 2:
						    if (sub_data.at(1) == "yes"){
							    keep_backup_file = 1;
						    } else{
							    keep_backup_file = 0;
						    }
                            continue;
					    case 3:
						    if (sub_data.at(1) == "yes"){
							    auto_backup = 1;
						    } else{
							    auto_backup = 0;
						    }
                            continue;
					    case 4:
						    auto_backup_freq = stoi(string(sub_data.at(1)));
                            continue;
					    case 5:
						    device_name = sub_data.at(1);
                            continue;
				    }
			    }
		    } else{
			    cout << "Failed to load settings" << endl;
		    }
	    }
	    
        // ------------------- Getters -------------------
		string get_server_addr(){
            return server_addr;
        }

		int get_port(){
            return port;
        }

		int get_send_file(){
            return send_file;
        }

		int get_keep_backup_file(){
            return keep_backup_file;
        }

		int get_auto_backup(){
            return auto_backup;
        }

		int get_auto_backup_freq(){
            return auto_backup_freq;
        }

		string get_device_name(){
            return device_name;
        }

		vector<string> get_folders_to_backup(){
            return folders_to_backup;
        }

        int get_updated(){
            return updated;        
        }
       // ------------------- Setters -------------------
        void set_server_addr(string value){
            server_addr = string(value);
            updated = 0;
        }

		void set_port(int value){
            port = value;
            updated = 0;
        }

		void set_send_file(int value){
            send_file = value;
            updated = 0;
        }

		void set_keep_backup_file(int value){
            keep_backup_file = value;
            updated = 0;
        }

		void set_auto_backup(int value){
            auto_backup = value;
            updated = 0;
        }

		void set_auto_backup_freq(int value){
            auto_backup_freq = value;
            updated = 0;
        }

		void set_device_name(string value){
            device_name = string(value);
            updated = 0;
        }

		void set_folders_to_backup(vector<string> value){
            folders_to_backup = value;
            updated = 0;
        }

        void save(){
            ostringstream settings_stream;
            string settings;
            string keep_backup_file = "yes";
            string auto_backup = "no";
            if (!this->keep_backup_file){ keep_backup_file = "no";}
            if (this->auto_backup){auto_backup = "yes";}
            settings_stream << "SERVERADDR:" << server_addr << endl;
            settings_stream << "PORT:" << port << endl;
            settings_stream << "KEEPBACKUPFILE:" << keep_backup_file << endl;
            settings_stream << "AUTOBACKUP:" << auto_backup << endl;
            settings_stream << "AUTOBACKUPFREQ:" << auto_backup_freq << endl;
            settings_stream << "DEVICENAME:" << device_name << endl;
            settings_stream << "THINGSTOBACKUP:";
            for (string s : folders_to_backup){
                settings_stream << endl << s;
            }
            settings = settings_stream.str();
            ofstream settings_file (SETTINGS_FILE_NAME, ios::out);
            settings_file << settings;
            updated = 1;
        }
};


int main(){
    string message = "This is an important component of STUBS, do not mess with it unless you know what you are doing.\n"
                     "This program comes with ABSOLUTELY NO WARRANTY; This is free software,\n"
                     "and you are welcome to redistribute it under certain conditions;\n"
                     "You should have received a copy of the GNU General Public License\n"
                     "along with this program.  If not, see <https://www.gnu.org/licenses/>.";
    cout << message << endl;
}

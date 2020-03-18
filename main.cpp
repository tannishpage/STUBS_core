#include <iostream>
#include <fstream>

using namespace std;

int main(){
	ifstream settings_file ("LICENSE", ios::in );
	char * raw_settings;
	streampos size;
	cout << "Made variables"<< endl;
	cout << "FIle open"<< endl;
	if (settings_file.is_open()){
		settings_file.seekg(0, ios::end);
		size = settings_file.tellg();
		cout << size << endl;
		raw_settings = new char [size];
		settings_file.seekg(0, ios::beg);
		settings_file.read(raw_settings, size);
		for (int i = 0; i <= size; i++){
			cout << raw_settings[i];
		}
	} else{
		cout << "Failed to load settings" << endl;
	}
}
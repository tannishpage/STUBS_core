#include "zlib/zlib.h"

#include <iostream>
#include <libtar.h>

#ifdef __unix__
#include<unistd.h>
#endif

#include  <string>
#include <vector>
#include <queue>

/* #ifdef !(__unix__)
#include "custome_library.h" or use Windows.h
#endif */

void make_tarball(vector<string> walk, TAR * tar) {

}

vector<string> do_walk(string directory) {
	vector<string> file_paths_found;
	queue<string> directories_to_visit;
	
	directories_to_visit.push(directory);
	while (!directories_to_visit.empty()) {
		// List the directory
		// Add all file paths to file_paths_found
		// Add all directories to directories_to_visit
	}
}

void compress_file(string input_file, string output_file) {
	FILE *input = fopen(input_file.c_str(), "rb")
	gzFile ouput = gzopen(output_file.c_str(), "wb")

	if (!input || !output) return -1;

	char buffer[128];
	int num_read = 0;
	unsigned long total_read = 0, total_wrote = 0;
	while ((num_read = fread(buffer, 1, sizeof(buffer), input)) > 0) {
		total_read += num_read;
		gzwrite(output, buffer, num_read);
	}
	fclose(input);
	gzclose(output);
}

int main() {

}
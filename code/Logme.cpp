#include "Logme.h"
#include <stdio.h>
#include <iostream>

#include <time.h>
#include <string>
#include <stdlib.h>
#include <ctime>

using namespace std;

Logme * Logme::theInstance = NULL;

FILE *fp;

Logme::Logme()
{
   //gx = x;
}
Logme::~Logme()
{
	//default destructor.
    fclose(fp);
}

inline std::string date_string()
{
    time_t rawtime;
    std::time(&rawtime);
    struct tm *tinfo = std::localtime(&rawtime);
    char date[12];
    char time[12];
    strftime(date, 12, "%F", tinfo);
    strftime(time, 12, "%X", tinfo);
    return std::string(date) + " " +  std::string(time);
}

void Logme::writeLogMS(const char* loc, const char* msg)
{
    if (fp == NULL) {
        fp = fopen("/Users/dengjinlong/Documents/8-tvguo/5-compilelog/cpp.log", "a");
    }

	if(fp)
	{
		fprintf(fp,"%s - %s: %s \n",
                date_string().c_str(),
                loc, msg );
	}
}

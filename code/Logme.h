#pragma once

#ifndef LOGME_H
#define LOGME_H
#include <stdlib.h>


class Logme
{
	//int gx;

public:


	static Logme * GetInstance()
	{
       if(theInstance == NULL)
		   theInstance = new Logme();
	   return theInstance;
	};

    ~Logme();

    void writeLogMS(const char* loc, const char* msg);

private:
	Logme();  //private constructor
	static Logme * theInstance;

};

#endif

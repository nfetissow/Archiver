#include <stdio.h>
int main()
{
	int childpid, parentpid;
	if((childpid = fork())== -1)
	{
		perror("Can't fork it!\n");
		return 1;
	}
	else if(childpid == 0)
	{
		while(1) printf(" Childpid = %d\n", getpid());
		return 0;
	}
	else 
	{
		while(1) printf(" Parentpid = %d\n", getpid());
		return 0;
	}
}


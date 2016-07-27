#include<stdio.h>
#include<string.h>
int main()
{
	char s[20200],ch;
	int n=0,f=0;
	while(f==0)
	{
		scanf("%c",&ch);
		if(ch==' ')
		{
			continue;
		}
		if(ch=='=')
		{
			f=1;
		}
		s[n++]=ch;
	}
	int i,num;
	scanf("%d",&num);
	int que=0,minus=0,plus=0;
	for(i=0;i<n;i++)
	{
		if(s[i]=='-')
		minus++;
		else if(s[i]=='+')
		plus++;
		else if(s[i]=='?')
		que++;
	}
	int k=num+minus;
	que=que-minus;
	int req=k/que,l,op,t;
	l=req;
//	printf("l is %d ",l);
	t=k%que;
	if(k%que!=0)
	req++;
	if(req>num||l==0)
	{
		printf("Impossible");
		return 0;
	}
	printf("Possible\n");
	printf("%d ",l);
	que--;
	for(i=1;i<n;i++)
	{
		if(s[i]=='?')
		{
			if(s[i-1]=='-')
			{
				printf("1 ");
			}
			else
			{
				if(que>t){
				printf("%d ",l);
				que--;}
				else
				{
					printf("%d ",req);
				}
			}
		}
		else
		printf("%c ",s[i]);
	}
	printf("%d",num);
	return 0;
}



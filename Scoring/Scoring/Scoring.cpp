#include <iostream>
#include<string>
#pragma warning(disable: 4996)
#include <windows.h> //유니코드 쓸때 필요
#include <locale.h> //유니코드 쓸때 필요
using namespace std;



class ScoreName
{
private:
	int score = 0;
	string name;

public:
	void operator=(ScoreName input)
	{
		score = input.score;
		name = input.name;
	}
	void setScore(int inputScore)
	{
		this->score = inputScore;
	}
	void setName(string inputName)
	{
		this->name = inputName;
	}
	int getScore()
	{
		return this->score;
	}
	string getName()
	{
		return this->name;
	}
};


int BreakHan(wchar_t *str, wchar_t *buffer, UINT nSize)
{

	//초성 
	static const wchar_t wcHead[] = { L'ㄱ', L'ㄲ', L'ㄴ', L'ㄷ',
		L'ㄸ', L'ㄹ', L'ㅁ', L'ㅂ',
		L'ㅃ', L'ㅅ', L'ㅆ', L'ㅇ',
		L'ㅈ', L'ㅉ', L'ㅊ', L'ㅋ',
		L'ㅌ', L'ㅍ', L'ㅎ' };

	//중성 
	static const wchar_t wcMid[] = { L'ㅏ', L'ㅐ', L'ㅑ', L'ㅒ',
		L'ㅓ', L'ㅔ', L'ㅕ', L'ㅖ',
		L'ㅗ', L'ㅘ', L'ㅙ', L'ㅚ',
		L'ㅛ', L'ㅜ', L'ㅝ', L'ㅞ',
		L'ㅟ', L'ㅠ', L'ㅡ', L'ㅢ', L'ㅣ' };

	//종성 
	static const wchar_t wcTail[] = { L' ', L'ㄱ', L'ㄲ', L'ㄳ',
		L'ㄴ', L'ㄵ', L'ㄶ', L'ㄷ',
		L'ㄹ', L'ㄺ', L'ㄻ', L'ㄼ',
		L'ㄽ', L'ㄾ', L'ㄿ', L'ㅀ',
		L'ㅁ', L'ㅂ', L'ㅄ', L'ㅅ',
		L'ㅆ', L'ㅇ', L'ㅈ', L'ㅊ',
		L'ㅋ', L'ㅌ', L'ㅍ', L'ㅎ' };

	UINT    pos = 0;

	while (*str != '\0')
	{
		if (*str == ' ')
		{
			++str;
		}
		else if (*str < 256)
		{
			if (pos + 2 >= nSize - 1)
				break;

			buffer[pos] = *str;
			++pos;
		}
		else
		{
			if (pos + 4 >= nSize - 1)
				break;
			if (*str >= 12593 && *str <= 12622)
			{
				buffer[pos] = *str;
				pos += 1;
			}
			else if (*str >= 12623 && *str <= 12643)
			{
				buffer[pos] = *str;
				pos += 1;
			}
			else
			{
				buffer[pos] = wcHead[(*str - 0xAC00) / (21 * 28)];
				buffer[pos + 1] = wcMid[(*str - 0xAC00) % (21 * 28) / 28];
				buffer[pos + 2] = wcTail[(*str - 0xAC00) % 28];
				pos += 3;
			}
		}
		++str;
	}
	buffer[pos] = '\0';


	return pos;
}

///////////////////////////////////////////////////////////////////////
//wchar_t 에서 char 로의 형변환 함수
char * ConvertWCtoC(wchar_t* str)
{
	//반환할 char* 변수 선언
	char* pStr;

	//입력받은 wchar_t 변수의 길이를 구함
	int strSize = WideCharToMultiByte(CP_ACP, 0, str, -1, NULL, 0, NULL, NULL);
	//char* 메모리 할당
	pStr = new char[strSize];

	//형 변환 
	WideCharToMultiByte(CP_ACP, 0, str, -1, pStr, strSize, 0, 0);
	return pStr;
}

///////////////////////////////////////////////////////////////////////
//char 에서 wchar_t 로의 형변환 함수
wchar_t* ConverCtoWC(char* str)
{
	//wchar_t형 변수 선언
	wchar_t* pStr;
	//멀티 바이트 크기 계산 길이 반환
	int strSize = MultiByteToWideChar(CP_ACP, 0, str, -1, NULL, NULL);
	//wchar_t 메모리 할당
	pStr = new WCHAR[strSize];
	//형 변환
	MultiByteToWideChar(CP_ACP, 0, str, strlen(str) + 1, pStr, strSize);
	return pStr;
}


wchar_t* ConverSTRtoWC(string* str)
{
	char* pStr_c=new char[str->length()+1];
	strcpy(pStr_c, str->c_str());
	return ConverCtoWC(pStr_c);
}

int scoring(string PhotoString, string ScoringTarget)
{
	//string으로 
	int score=0;
	wchar_t *ScoringTarget_WC;
	ScoringTarget_WC = ConverSTRtoWC(&ScoringTarget);

	wchar_t *PhotoString_WC;
	PhotoString_WC=ConverSTRtoWC(&PhotoString);
	for (int i = 0; PhotoString_WC[i] != 0; i++)
	{
		for(int j=0;ScoringTarget_WC[j]!=0;j++)
			if (PhotoString_WC[i] == ScoringTarget_WC[j])
			{
				score += 10;
				ScoringTarget_WC[j] = '-';
				break;
			}
	}

	ScoringTarget_WC = ConverSTRtoWC(&ScoringTarget);//복구


	wchar_t Broken_PhotoString[4096];
	wchar_t Broken_ScoringTarget[4096];
	BreakHan(ScoringTarget_WC, Broken_ScoringTarget, sizeof Broken_ScoringTarget);
	BreakHan(PhotoString_WC, Broken_PhotoString, sizeof Broken_PhotoString);
	
	for (int i = 0; PhotoString_WC[i] != 0; i++)
	{
		for (int j = 0; ScoringTarget_WC[j] != 0; j++)
			if (PhotoString_WC[i] == ScoringTarget_WC[j])
			{
				score += 2;
				ScoringTarget_WC[j] ='-';
				break;
			}
			else
			{
				score -= 1;
			}
	}
	
	/*
	string ScoringTarget_temp=ScoringTarget;
	string PhotoString_temp;
	//완전한 한글 비교 scoring
	for (int i = 0; i < PhotoString.length(); i += 2)
	{
		for (int j = 0; j < ScoringTarget_temp.length(); j += 2)
		{
			if (PhotoString[i] == ScoringTarget_temp[j] && PhotoString[i + 1] == ScoringTarget_temp[j + 1])//완전한 한글자가 맞으면 10점 
			{
				score += 10;
				ScoringTarget_temp[j] = 0;
				ScoringTarget_temp[j + 1] = 0;
				break;
			}
		}
		cout << "";
	}

	
	//자음과 모음을 자르기위해 wchar_t로 변환
	wchar_t Broken_PhotoString[4096];
	wchar_t Broken_ScoringTarget[4096];
	wchar_t* buf;
	bool CheckSpell = 0;
	buf = ConverSTRtoWC(&PhotoString);
	BreakHan(buf, Broken_PhotoString,sizeof Broken_PhotoString);
	buf = ConverSTRtoWC(&ScoringTarget);
	BreakHan(buf, Broken_ScoringTarget, sizeof Broken_ScoringTarget);
	


	PhotoString_temp=ConvertWCtoC(Broken_PhotoString);
	ScoringTarget_temp= ConvertWCtoC(Broken_ScoringTarget);

	int SpIndex = 0;
	while ((SpIndex = PhotoString_temp.find(" ")) != -1) {
		PhotoString_temp.replace(SpIndex, 1, "");
		SpIndex = 0;
	}
	while ((SpIndex = ScoringTarget_temp.find(" ")) != -1) {
		ScoringTarget_temp.replace(SpIndex, 1, "");
		SpIndex = 0;
	}
	
	

	for (int i = 0; i < PhotoString_temp.length(); i += 2)
	{
		for (int j = 0; j < ScoringTarget_temp.length(); j += 2)
		{
			if (PhotoString_temp[i] == ScoringTarget_temp[j] && PhotoString_temp[i + 1] == ScoringTarget_temp[j + 1])//완전한 한글자가 맞으면 10점 
			{

				ScoringTarget_temp[j] = 0;
				ScoringTarget_temp[j + 1] = 0;
				CheckSpell = 1;
				break;
			}
		}
		if (CheckSpell == 0)
		{
			score -= 2;
		}
		else
		{
			score += 2;
			CheckSpell = 0;
		}
		cout << "";
	}
	*/
	
	return score;
}
void SortRank(ScoreName *input, int index)
{
	ScoreName temp;
	int j = 0;
	int i = 0;

	for (i = 1; i < index; i++)
	{
		j = i;
		while (j > 0 && input[j - 1].getScore() > input[j].getScore()) {
			temp = input[j];
			input[j] = input[j - 1];
			input[j - 1] = temp;
			j--;
		}
	}

}

wchar_t* main(int argc,char* argv[])
{
	//////////////////////////////////////////////////////
	string PhotoString=argv[1];//original string from photo
	char* PhotoString_c=new char [PhotoString.length()+1];

	int SpIndex = 0;//delete space & spacial character
	while (1) {
		if ((SpIndex = PhotoString.find(" ")) != -1){
			PhotoString.replace(SpIndex, 1, "");
		}
		else if ((SpIndex = PhotoString.find(".")) != -1){
			PhotoString.replace(SpIndex, 1, "");
		}
		else if ((SpIndex = PhotoString.find("!")) != -1){
			PhotoString.replace(SpIndex, 1, "");
		}
		else if ((SpIndex = PhotoString.find("'")) != -1) {
			PhotoString.replace(SpIndex, 1, "");
		}
		else if ((SpIndex = PhotoString.find(",")) != -1) {
			PhotoString.replace(SpIndex, 1, "");
		}
		else if ((SpIndex = PhotoString.find("?")) != -1) {
			PhotoString.replace(SpIndex, 1, "");
		}
		else if ((SpIndex = PhotoString.find("|")) != -1) {
			PhotoString.replace(SpIndex, 1, "");
		}
		else if ((SpIndex = PhotoString.find("^")) != -1) {
			PhotoString.replace(SpIndex, 1, "");
		}
		else if ((SpIndex = PhotoString.find("┌")) != -1) {
			PhotoString.replace(SpIndex, 1, "");
		}
		else if ((SpIndex = PhotoString.find(":")) != -1) {
			PhotoString.replace(SpIndex, 1, "");
		}
		else
		{
			break;
		}
		SpIndex = 0;
	}
	
	strcpy(PhotoString_c, PhotoString.c_str());//string->char*
	//////////////////////////////////////////////////////


	//////////////////////////////////////////////////////
	string* MenuList=new string[argc-2];//menulist string array
	for (int i = 2; i < argc; i++)//MenuList save
		MenuList[i - 2] = argv[i];
	///////////////////////////////////////////////////////

	ScoreName* Rank = new ScoreName[argc - 1];
	

	for (int i = 0; i < argc - 2; i++)
	{
		Rank[i].setScore(scoring(PhotoString, MenuList[i]));
		Rank[i].setName(MenuList[i]);
	}

	for (int i = 0; i < argc - 2; i++)
	{
		cout << Rank[i].getName() << " " << Rank[i].getScore() << endl;
	}
	SortRank(Rank,argc-2);
	for (int i = 0; i < argc - 2; i++)
	{
		cout << Rank[i].getName() << " " << Rank[i].getScore() << endl;
	}
	cout <<"\n\nResult:"<<Rank[0].getName()<<endl<<endl;
	return ConverSTRtoWC(&Rank[0].getName());
}
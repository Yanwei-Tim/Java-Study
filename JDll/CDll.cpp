#include <stdio.h>
#include <string>

#if 0

void __declspec(dllexport) _stdcall showVoidContent()
{

}

std::string __declspec(dllexport) _stdcall showReturnContent()
{
	return std::string("11111111111111111");
}
std::string __declspec(dllexport) _stdcall getOneContent(std::string one, std::string two)
{
	return std::string("one: ");
	//+ one +std::string("; two: ") + two;
};
double __declspec(dllexport) _stdcall getTwoDouble(double one, double two, std::string three)
{
	return one + two + atof(three.c_str());
}
#endif
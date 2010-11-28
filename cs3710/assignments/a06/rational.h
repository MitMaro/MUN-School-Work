/*#########################################################
  ##  CS 3710 (Winter 2010), Assignment #6               ##
  ##   Script File Name: rational.h                      ##
  ##       Student Name: Tim Oram                        ##
  ##         Login Name: oram                            ##
  ##              MUN #: #########                       ##
  #########################################################*/

#include <iostream>

class Rational {
	private:
		int num;
		int den;
		int gcd() const;
		void simplfy();

	public:
		Rational ();
		Rational (const int, const int);
		Rational operator + (Rational);
		Rational operator - (Rational);
		void assign(const int, const int);
		int getNumerator() const;
		int getDenominator() const;
};


// overload operator for outputting a rational
template <typename CharT, typename Traits>
std::basic_ostream<CharT, Traits>& operator<<(std::basic_ostream<CharT, Traits>& out, const Rational& r){
	if(r.getDenominator() == 1){
		return out << r.getNumerator();
	}
	else{
		return out << r.getNumerator() << "/" << r.getDenominator();
	}
}

Rational::Rational (){
	this->num = 0;
	this->den = 1;
}

Rational::Rational (const int num, const int den){
	this->num = num;
	this->den = den;
	this->simplfy();
}

Rational Rational::operator+(const Rational r){
	this->num = this->num * r.den + r.num * this->den;
	this->den *= r.den;
	this->simplfy();
}

Rational Rational::operator-(const Rational r){
	this->num = this->num * r.den - r.num * this->den;
	this->den *= r.den;
	this->simplfy();
}

void Rational::assign(const int num, const int den){
	this->num = num;
	this->den = den;
	this->simplfy();
}

int Rational::getNumerator() const{
	return this->num;
}

int Rational::getDenominator() const{
	return this->den;
}

void Rational::simplfy(){
	if(this->den < 0){
		this->num = -this->num;
		this->den = -this->den;
	}
	if(this-> num != 0){
		int gcd = this->gcd();
		this->num /= gcd;
		this->den /= gcd;
	}
}

// the gcd algo
int Rational::gcd() const{
	int a = this->num;
	int b = this->den;
	if(b == 0){
		return a;
	}
	if(a < 0){
		a = -a;
	}
	if(b < 0){
		b = -b;
	}
	while(true) {
		a = a % b;
		if(a == 0){
			return b;
		}
		b = b % a;
		if( b == 0 ){
			return a;
		}
	}
}

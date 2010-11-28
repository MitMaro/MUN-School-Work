#/bin/bash
echo "Question 1"
gcc addMat.c
./a.out
./a.out mat1.dat mat2.dat out1.dat
cat out1.dat
./a.out mat1.dat mat3.dat out2.dat
cat out2.dat
./a.out mat2.dat mat3.dat out3.dat
cat out3.dat
./a.out mat4.dat mat5.dat out4.dat
cat out4.dat
./a.out mat6.dat mat7.dat out5.dat
cat out5.dat

echo
echo
echo "Question 2"
gcc itemCount.c
./a.out
./a.out 10 item1.dat
./a.out 10 item2.dat
./a.out 10 item3.dat
./a.out 10 item4.dat


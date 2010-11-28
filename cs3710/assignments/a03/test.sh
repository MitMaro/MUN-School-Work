#!/bin/bash


echo "Question #1"
python convert.py

python convert.py numdata1.dat out1.dat
cat out1.dat
echo

python convert.py numdata2.dat out2.dat
cat out2.dat
echo

python convert.py numdata3.dat out3.dat
cat out3.dat
echo

python convert.py numdata4.dat out4.dat
cat out4.dat


echo
echo
echo
echo "Question #2"

python selectCol.py
cat coldata1.dat
echo

python selectCol.py coldata1.dat col1.dat 3:L:6
cat col1.dat
echo

python selectCol.py coldata1.dat col2.dat 4:R:10
cat col2.dat
echo

python selectCol.py coldata1.dat col3.dat 2:R:3 2:R:2 1:R:8 5:L:5
cat col3.dat
echo

python selectCol.py coldata1.dat col4.dat 5:L:10 4:L:10 3:L:10 2:L:10 1:L:10
cat col4.dat
echo

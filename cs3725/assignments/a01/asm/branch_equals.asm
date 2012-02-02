addi $t0, $zero, 10
addi $t1, $zero, 10
addi $t2, $zero, 5
add $s0, $zero, $zero
add $s1, $zero, $zero
add $s2, $zero, $zero

beq $t0, $t2, label1
addi $s0, $zero, 1
beq $t0, $t1, label1
addi $s1, $zero, 1 #skipped
label1:
add $s2, $zero, 1 

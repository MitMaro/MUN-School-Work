addi $t0, $zero, 10
add $s0, $zero, $zero
loop:
addi $s0, $s0, 1 
beq $s0, $t0, brk
j loop
brk:
addi $s1, $zero, 10
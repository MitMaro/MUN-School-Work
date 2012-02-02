.data
var1: .word 10
var2: .word 5

.text
addi $t0, $zero, 15
lw $s0, var1($zero)
sw $t0, var2($zero)
lw $s2, var1($zero)

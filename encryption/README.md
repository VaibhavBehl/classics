open RUN.bat
OR
execute-
$ javac Encr.java
$ java Encr

NOTE- NEED TO HAVE THE GENERATED CLASS FILES ON CLASSPATH
********************************************************************************************************************
*********************************************************************************************************************
SOFTWARE - file encrypter
MADE BY - vaibhav-B
DATE OF COMPLETION - 01 / 06 / 2010

DESCRIPTION - This is a basic encryption software which can encrypt single file or directories(recursively).Here we accept
a PASSKEY from the user which is used to encrypt the data.This passkey is not stored in the ENCRYPTED file making it 
impossible to CRACK as the attacker has no knowledge of (ORIGINAL DATA and PASSKEY), and assuming he has access to encrypted
data and he has high computational capabilities!! still he cant CRACK it because he can't know which data he decrypted is 
correct. [UPDATE: although this method of simple x-or fares poorly when size of passkey is small and the DATA could be guessed 
(like starting bits of some well known formats (.pdf, .dox))

-This technique is a VARIABLE bit enryption, as the bit size depends on the user PASSKEY size. this gives additional 
as attacker cant know if it was a (128bit or 1024bit) encryption!!

REAL ENVIRONMENT TESTING - 
TESTED ON THE FOLLOWING CONF.->
intel pentium dual CPU @ 2.20 GHz
2GB DDR2 800MHz RAM
250GB SATA HD

RESULT ->
SINGLE FILE- 32.8MB encrypted in 2 sec
DIRECTORY(4 sub-directories and 7 files) - 137MB(total) encryptd in 9 sec  

***********************************************************************************************************************
**********************************************************************************************************************/
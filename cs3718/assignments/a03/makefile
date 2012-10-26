all: clean compile package

compile:
	javac -target 1.6 -g -d ./bin/ \
src/ca/mitmaro/lang/StringUtils.java \
src/ca/mitmaro/commandline/help/Message.java \
src/ca/mitmaro/commandline/help/System.java \
src/ca/mitmaro/commandline/command/annotation/Command.java \
src/ca/mitmaro/commandline/command/annotation/Help.java \
src/ca/mitmaro/commandline/command/annotation/Helps.java \
src/ca/mitmaro/commandline/command/DefaultCommands.java \
src/ca/mitmaro/commandline/term/Terminal.java \
src/ca/mitmaro/commandline/userinterface/ExistentFilePrompt.java \
src/ca/mitmaro/commandline/userinterface/FilePrompt.java \
src/ca/mitmaro/commandline/userinterface/NumberedMenu.java \
src/ca/mitmaro/commandline/userinterface/Prompt.java \
src/ca/mitmaro/commandline/userinterface/SimplePrompt.java \
src/ca/mitmaro/commandline/userinterface/TextBased.java \
src/ca/mitmaro/commandline/userinterface/YesNoQuestion.java \
src/ca/mitmaro/commandline/CommandLine.java \
src/ca/mitmaro/ldb/exception/MissingPaperException.java \
src/ca/mitmaro/ldb/exception/InvalidListException.java \
src/ca/mitmaro/ldb/entity/Book.java \
src/ca/mitmaro/ldb/entity/BookChapter.java \
src/ca/mitmaro/ldb/entity/ConferencePaper.java \
src/ca/mitmaro/ldb/entity/JournalPaper.java \
src/ca/mitmaro/ldb/entity/List.java \
src/ca/mitmaro/ldb/entity/Paper.java \
src/ca/mitmaro/ldb/entity/PHDThesis.java \
src/ca/mitmaro/ldb/entity/SoftReferenceList.java \
src/ca/mitmaro/ldb/Application.java \
src/ca/mitmaro/ldb/Commands.java \
src/ca/mitmaro/ldb/CommandLineInterface.java \
src/LDB.java \
src/LDBTest.java

clean:
	rm -rf ./bin/*

package:
	jar -cfm LDB.jar src/manifest.txt -C bin/ .
	
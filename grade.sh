# Draw a picture/take notes on the directory structure that's set up after
# getting to this point

# Then, add here code to compile and run, and do any post-processing of the
# tests

rm -rf student-submission
rm -rf grading-area

mkdir grading-area

git clone $1 student-submission 2> git-output.txt
echo 'Finished cloning'

if [[ -f student-submission/ListExamples.java ]]
then
    cp student-submission/ListExamples.java grading-area/
    cp TestListExamples.java grading-area/
else 
	echo “Missing student-submission/ListExamples.java, did you forget the file or misname it?”
	exit 1
fi 

cd grading-area

CPATH='.:../lib/hamcrest-core-1.3.jar:../lib/junit-4.13.2.jar'

javac -cp $CPATH *.java

if [[	$? -ne 0	]]
then
	echo “The program failed to compile, see compile error above”
	exit 1
fi

java -cp $CPATH org.junit.runner.JUnitCore TestListExamples > junit-output.txt

if ([	$? -ne 0	])
then
	last_line=$(grep . junit-output.txt | tail -n 1)

	tests_run=$(echo $last_line | grep -oE "[0-9]+" | head -n 1)

	failures=$(echo $last_line | grep -oE "[0-9]+" | tail -n 1)
else
	last_line=$(grep . junit-output.txt | tail -n 1)

	tests_run=$(echo $last_line | grep -oE "[0-9]+" | head -n 1)

	failures=0
fi

grade=$((((tests_run-failures)*100/tests_run)))

echo -e "Tests failed: $failures\nTests ran: $tests_run\nSubmission grade: $grade%" 2>&1 | tee grade.txt
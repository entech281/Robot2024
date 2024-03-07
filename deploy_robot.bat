if [%1]==[] goto usage
git remote set-url origin git@10.2.81.10:entech281/Robot2024.git
git fetch --all
git pull
git checkout -f %1
.\gradlew build
.\gradlew deploy

goto :eof
:usage
@echo
@echo Deploys the selected branch from repository to the robot.
@echo Usage: %0 ^<BranchName^>
exit /B 1


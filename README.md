# Robot2024
team 281 2024 robot

## Coding/Architecture decisions for 2024
* entech package for all classes that can be copied from one season to the next. No code in here can be season specific
* Classes we want to keep:  OperatorInterface, RobotConstants, EntechCommand, EntchSubsystem
* Subsystems should be the source of data for them ( no value objects). N/A if we use Advantage Kit
* use pre-built 2465 swerve drive, but modified to use our base classes

## Things we want to prototype
* Advantage scope logging/advantage kit
* Command factory sub-scoping ( make it easier to find commands for a given subsystem
* standardize entech subsyste

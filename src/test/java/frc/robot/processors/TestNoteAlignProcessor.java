package frc.robot.processors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.note_detector.NoteDetectorOutput;
import frc.robot.processors.NoteAlignProcessor;

public class TestNoteAlignProcessor {

  @Test
  void noNote() {
    DriveInput di = new DriveInput();
    NoteDetectorOutput no = new NoteDetectorOutput();
    NoteAlignProcessor ni = new NoteAlignProcessor();
    assertEquals(true, ni.alignToNote(di, no)==di);
  }

  @Test
  void centered() {
    DriveInput di = new DriveInput();
    NoteDetectorOutput no = new NoteDetectorOutput();
    NoteAlignProcessor ni = new NoteAlignProcessor();
    assertEquals(0, ni.alignToNote(di, no).getRotation(), 0.01);
  }

  @Test
  void offsetLeft() {
    DriveInput di = new DriveInput();
    NoteDetectorOutput no = new NoteDetectorOutput();
    NoteAlignProcessor ni = new NoteAlignProcessor();
    assertEquals(true, ni.alignToNote(di, no).getRotation()<0);
  }  

  @Test
  void offsetRight() {
    DriveInput di = new DriveInput();
    NoteDetectorOutput no = new NoteDetectorOutput();
    NoteAlignProcessor ni = new NoteAlignProcessor();
    assertEquals(true, ni.alignToNote(di, no).getRotation()>0);
  }  

}

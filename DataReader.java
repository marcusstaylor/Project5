// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Shane Malino (shanerm)

package prj5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This method will read the files that contain the song's stats as well as the
 * survey given to the students.
 * 
 * @author Shane Malino (shanerm)
 * @version 2019.04.15
 */
public class DataReader {
    private LinkedList<Song> songList;
    private LinkedList<Student> studentList;
    private int responses;
    private DataProcessor processor;


    /**
     * This method makes a new DataReader object that reads the student survey
     * and the song files.
     * 
     * @param student
     *            The student survey file.
     * @param song
     *            The song data file.
     * @throws FileNotFoundException
     *             In case the file cannot be found.
     */
    public DataReader(String student, String song)
        throws FileNotFoundException {
        songList = readSongFile(song);
        responses = songList.getSize() * 2;
        studentList = readStudentFile(student);
        processor = new DataProcessor(songList, studentList);
    }


    /**
     * getProcessor() will return to the client code the processor that
     * has the data that was read in through the reader
     */
    public DataProcessor getProcessor() {
        return processor;
    }


    /**
     * getSongList() will return the song list
     * 
     * @return songlist the list of songs
     */
    public LinkedList<Song> getSongList() {
        return this.songList;
    }


    /**
     * This method reads the student survey file that has all of the responses
     * by the students that answers whether if they have heard the song before
     * and if they liked it.
     * 
     * @param student
     *            The student survey file.
     * @return The list of the student surveys.
     * @throws FileNotFoundException
     *             In case the file is not found.
     */
    private LinkedList<Student> readStudentFile(String student)
        throws FileNotFoundException {
        File newFile = new File(student);
        Scanner file = new Scanner(newFile);
        studentList = new LinkedList<Student>();

        String line = null;
        file.nextLine();
        while (file.hasNextLine()) {
            line = file.nextLine();
            String[] line2 = line.split(",");
            String[] resp = new String[responses];
            for (int i = 5; i < line2.length; i++) {
                resp[i - 5] = line2[i];
            }
            int num = Integer.valueOf(line2[0]);
            String date = line2[1];
            Hobby hob = enumHob(line2[4]);
            Major maj = enumMaj(line2[2]);
            Region reg = enumReg(line2[3]);
            Student s = new Student(num, date, maj, hob, reg, resp);
            studentList.add(s);
        }
        file.close();
        return studentList;
    }


    /**
     * This method reads the song data that has its name, artist, year it was
     * released, and the genre of the song.
     * 
     * @param song
     *            The song file.
     * @return The list of songs.
     * @throws FileNotFoundException
     *             In case the file is not found.
     */
    private LinkedList<Song> readSongFile(String song)
        throws FileNotFoundException {
        File newFile = new File(song);
        Scanner file = new Scanner(newFile);
        songList = new LinkedList<Song>();

        String line = null;
        file.nextLine();
        while (file.hasNextLine()) {
            line = file.nextLine();
            String[] line2 = line.split(",");
            String title = line2[0];
            String artist = line2[1];
            int year = Integer.valueOf(line2[2]);
            String genre = line2[3];
            Song s = new Song(title, artist, genre, year);
            songList.add(s);
        }
        file.close();
        return songList;
    }


    /**
     * This method is a helper method to readStudentFile() so that it can
     * determine what is their hobby.
     * 
     * @param input
     *            A string that will determine what is the student's hobby.
     * @return The student's hobby.
     */
    private Hobby enumHob(String input) {
        switch (input) {
            case "reading":
                return Hobby.READING;
            case "music":
                return Hobby.MUSIC;
            case "art":
                return Hobby.ART;
            default:
                return Hobby.SPORTS;
        }
    }


    /**
     * This method is a helper method to readStudentFile() so that it can
     * determine what is their major.
     * 
     * @param input
     *            A string that will determine what is the student's major.
     * @return The student's major.
     */
    private Major enumMaj(String input) {
        switch (input) {
            case "Computer Science":
                return Major.CS;
            case "Math or CMDA":
                return Major.MATH;
            case "Other Engineering":
                return Major.ENGINEERING;
            default:
                return Major.OTHER;
        }
    }


    /**
     * This method is a helper method to readStudentFile() so that it can
     * determine what is their region that they live.
     * 
     * @param input
     *            A string that will determine what is the student's region that
     *            they live.
     * @return The student's region that they live.
     */
    private Region enumReg(String input) {
        switch (input) {
            case "Northeast":
                return Region.NORTHEAST;
            case "Southeast":
                return Region.SOUTHEAST;
            case "United States (other than Southeast or Northwest)":
                return Region.OTHER_US;
            default:
                return Region.OUTSIDE_US;
        }
    }
}

package sfedu.xast.api;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import org.slf4j.*;
import sfedu.xast.models.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataProviderCsv  {

    Logger logger = LoggerFactory.getLogger(DataProviderCsv.class);

    /**
     * write records in csv file
     * @param data
     * @throws IOException
     */
    public static void writeToCsv(List<String[]> data, String csvFilePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFilePath));
             CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeAll(data);
        }
    }

    /**
     * read records from csv file
     * @return
     * @throws IOException
     * @throws CsvException
     */
    public  List<String[]> readFromCsv(String csvFilePath) throws IOException, CsvException {
        List<String[]> data = new ArrayList<>();
        if (Files.exists(Paths.get(csvFilePath))) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                 CSVReader csvReader = new CSVReader(reader)) {
                data = csvReader.readAll();
            }
        }
        return data;
    }

    /**
     * creating record in csv file
     * @param persInf
     * @param csvFilePath
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean createPersInf(PersInf persInf, String csvFilePath) throws IOException, CsvException {
        if(persInf==null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.add(new String[]{
                    persInf.getId(),
                    persInf.getSurname(),
                    persInf.getName(),
                    persInf.getPhoneNumber(),
                    persInf.getEmail()
            });
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from csv file using personal id
     * @param persInf
     * @param id
     * @param csvFilePath
     * @return PersINf object
     * @throws IOException
     * @throws CsvException
     */
    public PersInf readPersInf(PersInf persInf, String id, String csvFilePath) throws IOException, CsvException {
        if(persInf==null){
            throw new CsvException("PersInf object must not be null");
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            for (String[] row : data){
                if(row[0].equals(id)){
                    persInf.setId(row[0]);
                    persInf.setSurname(row[1]);
                    persInf.setName(row[2]);
                    persInf.setPhoneNumber(row[3]);
                    persInf.setEmail(row[4]);
                    return persInf;
                }
            }
            throw new CsvException("Can't find person with id " + id);
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in csv file by personal id
     * @param updatedPersInf
     * @param csvFilePath
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean updatePersInf(PersInf updatedPersInf, String csvFilePath) throws IOException, CsvException {
        if (updatedPersInf == null) {
            throw new CsvException("PersInf object must not be null");
        }
        try {
            List<String[]> data = readFromCsv(csvFilePath);
            boolean found = false;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[0].equals(updatedPersInf.getId())) {
                    data.set(i, new String[]{
                            updatedPersInf.getId(),
                            updatedPersInf.getSurname(),
                            updatedPersInf.getName(),
                            updatedPersInf.getPhoneNumber(),
                            updatedPersInf.getEmail()
                    });
                    found = true;
                    break;
                }
            }
            writeToCsv(data, csvFilePath);
            return found;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * delete records from csv file using id
     * @param id
     * @param csvFilePath
     * @return
     */
    public boolean deletePersInf(String id, String csvFilePath) {
        if(id == null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.removeIf(row -> row[0].equals(id));
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }

    }

    /**
     * creating record in csv file
     * @param persInf
     * @param csvFilePath
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean createProfInf(ProfInf profInf, PersInf persInf, String csvFilePath) throws IOException, CsvException {
        if(persInf == null || profInf   == null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.add(new String[]{
                    persInf.getId(),
                    profInf.getSkillName(),
                    profInf.getSkillDescription(),
                    String.valueOf(profInf.getCost()),
                    profInf.getPersDescription(),
                    String.valueOf(profInf.getExp()),
                    String.valueOf(profInf.getRating())
            });
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from csv file using personal id
     * @param profInf
     * @param id
     * @param csvFilePath
     * @return PersINf object
     * @throws IOException
     * @throws CsvException
     */
    public ProfInf readProfInf(ProfInf profInf, String id, String csvFilePath) throws IOException, CsvException {
        if(profInf == null){
            throw new CsvException("ProfInf object must not be null");
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            for (String[] row : data){
                if(row[0].equals(id)){
                    profInf.setSkillName(row[1]);
                    profInf.setSkillDescription(row[2]);
                    profInf.setCost(Double.valueOf(row[3]));
                    profInf.setPersDescription(row[4]);
                    profInf.setExp(Double.valueOf(row[5]));
                    profInf.setRating(Double.valueOf(row[6]));
                    return profInf;
                }
            }
            throw new CsvException("Can't find person with id " + id);
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in csv file by personal id
     * @param updatedProfInf
     * @param csvFilePath
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean updateProfInf(ProfInf updatedProfInf, String csvFilePath) throws IOException, CsvException {
        if (updatedProfInf == null) {
            throw new CsvException("ProfInf object must not be null");
        }
        try {
            List<String[]> data = readFromCsv(csvFilePath);
            boolean found = false;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[0].equals(updatedProfInf.getPersId())) {
                    data.set(i, new String[]{
                            updatedProfInf.getPersId(),
                            updatedProfInf.getSkillName(),
                            updatedProfInf.getSkillDescription(),
                            String.valueOf(updatedProfInf.getCost()),
                            updatedProfInf.getPersDescription(),
                            String.valueOf(updatedProfInf.getExp()),
                            String.valueOf(updatedProfInf.getRating())
                    });
                    found = true;
                    break;
                }
            }
            writeToCsv(data, csvFilePath);
            return found;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * delete records from csv file using id
     * @param id
     * @param csvFilePath
     * @return
     */
    public boolean deleteProfInf(String id, String csvFilePath) {
        if(id == null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.removeIf(row -> row[0].equals(id));
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    //TODO написать метод readProfInfBySkillName/printProfInfList/readProfInfWithId(300-я строка)
}

package com.util;

import java.time.ZonedDateTime;
import java.util.NavigableSet;
import java.util.TreeSet;

import com.model.Person;
import com.model.StudyGroup;

/**
 * Operates the collection itself.
 */
public class CollectionManager {
    private TreeSet<StudyGroup> studyGroupCollection =  new TreeSet<>();
    private ZonedDateTime lastInitTime;
    private ZonedDateTime lastSaveTime;
    private FileManager fileManager;

    public CollectionManager(FileManager fileManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;
        
        loadCollection();
    }
    
    /**
     * @return The collecton itself.
     */
    public NavigableSet<StudyGroup> getCollection() {
        return studyGroupCollection;
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public ZonedDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public ZonedDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return studyGroupCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return studyGroupCollection.size();
    }

    /**
     * @return The first element of the collection or null if collection is empty.
     */
    public StudyGroup getFirst() {
        if (studyGroupCollection.isEmpty()) return null;
        return studyGroupCollection.first();
    }

    /**
     * @return The last element of the collection or null if collection is empty.
     */
    public StudyGroup getLast() {
        if (studyGroupCollection.isEmpty()) return null;
        return studyGroupCollection.last();
    }

    /**
     * @param id ID of the studyGroup.
     * @return A studyGroup by his ID or null if studyGroup isn't found.
     */
    public StudyGroup getById(Integer id) {
        for (StudyGroup studyGroup : studyGroupCollection) {
            if (studyGroup.getId().equals(id)) return studyGroup;
        }
        return null;
    }

    /**
     * @param studyGroupToFind A studyGroup who's value will be found.
     * @return A studyGroup by his value or null if studyGroup isn't found.
     */
    public StudyGroup getByValue(StudyGroup studyGroupToFind) {
        for (StudyGroup studyGroup : studyGroupCollection) {
            if (studyGroup.similar(studyGroupToFind)) return studyGroup;
        }
        return null;
    }

    /**
     * Adds a new studyGroup to collection.
     * @param studyGroup A studyGroup to add.
     */
    public void addToCollection(StudyGroup studyGroup) {
        studyGroupCollection.add(studyGroup);
    }

    /**
     * Removes a new studyGroup to collection.
     * @param studyGroup A studyGroup to remove.
     */
    public void removeFromCollection(StudyGroup studyGroup) {
        studyGroupCollection.remove(studyGroup);
    }

    /**
     * Remove marines greater than the selected one.
     * @param studyGroup A studyGroup to compare with.
     */
    public void removeGreater(StudyGroup studyGroup) {
        studyGroupCollection.removeIf(studyGroup1 -> studyGroup1.compareTo(studyGroup) > 0);
    }

    public void removeLower(StudyGroup studyGroup) {
        studyGroupCollection.removeIf(studyGroup1-> studyGroup1.compareTo(studyGroup) < 0);
    }

    public void removeAllByShouldBeExpelled(Long shouldBeExpelled) {
        studyGroupCollection.removeIf(studyGroup -> studyGroup.getExpelledStudents() > shouldBeExpelled);
    }

    public long countByGroupAdmin(Person person) {
        return studyGroupCollection.stream().filter(studyGroup -> studyGroup.getGroupAdmin().equals(person)).count();
    }

    public String greaterThanExpelledStudentsFilteredInfo(Long expelledStudents) {
        StringBuilder info = new StringBuilder();
        for (StudyGroup studyGroup : studyGroupCollection) {
            if (studyGroup.getExpelledStudents() >= expelledStudents) {
                info.append(studyGroup).append("\n\n");
            }
        }
        return info.toString().trim();
    }

    /**
     * Clears the collection.
     */
    public void clearCollection() {
        studyGroupCollection.clear();
    }

    /**
     * Generates next ID. It will be (the bigger one + 1).
     * @return Next ID.
     */
    public Integer generateNextId() {
        if (studyGroupCollection.isEmpty()) return 1;
        return studyGroupCollection.last().getId() + 1;
    }

    /**
     * Saves the collection to file.
     */
    public void saveCollection() {
            fileManager.writeCollection(studyGroupCollection);
            lastSaveTime = ZonedDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection() {
        studyGroupCollection = fileManager.readCollection();
        lastInitTime = ZonedDateTime.now();
    }

    @Override
    public String toString() {
        if (studyGroupCollection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (StudyGroup studyGroup : studyGroupCollection) {
            info.append(studyGroup);
            if (studyGroup != studyGroupCollection.last()) info.append("\n\n");
        }
        return info.toString();
    }
}

package Repo;

import Domain.Entity;

public interface Repository<ID,E extends Entity<ID>> {
    /**
     *
     * @param id - the id of the entity to look for
     * @return the object with the given id
     *          null if it was not found
     */
    default E getOne(ID id){
        return null;
    }

    /**
     *
     * @return all the entities in the database
     */
    default Iterable<E> getAll(){
        return null;
    }

    /**
     *
     * @param entity - the entity to be stored in the database
     * @return entity, if the entity was not added succesfully
     *          null, if it was added
     */
    default E add(E entity){
        return null;
    }

    /**
     *
     * @param id - the id of the entity to be deleted
     * @return
     */
    default E delete(ID id){
        return null;
    }

    /**
     *
     * @param entity
     * @return
     */
    default E update(E entity){
        return null;
    }
}

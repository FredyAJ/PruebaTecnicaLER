package org.example.service;

import java.time.LocalDate;
import java.util.List;

import org.example.domain.Person;
import org.example.enumModel.FilterType;
import org.example.model.Filter;
import org.example.model.PaginationDTO;
import org.example.model.PersonDTO;
import org.example.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.Valid;

@Service
public class PersonService {
    @PersistenceContext
    private EntityManager entityManager;
    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDTO> findAll() {
        final List<Person> persons = personRepository.findAll(Sort.by("document"));

        return persons.stream()
                .map(person -> mapToDTO(person, new PersonDTO()))
                .toList();
    }

    public PersonDTO get(final String document) {
        return personRepository.findById(document)
                .map(person -> mapToDTO(person, new PersonDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el manejador"));
    }

    public String create(final PersonDTO personDTO) {
        final Person person = new Person();
        mapToEntity(personDTO, person);
        person.setDocument(personDTO.getDocument());
        return personRepository.save(person).getDocument();
    }

    public void update(final String document, final PersonDTO personDTO) {
        final Person person = personRepository.findById(document)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró al usuario"));
        mapToEntity(personDTO, person);
        personRepository.save(person);
    }

    public void delete(final String document) {
        personRepository.deleteById(document);
    }

    private PersonDTO mapToDTO(final Person person, final PersonDTO personDTO) {
        personDTO.setDocument(person.getDocument());
        personDTO.setName(person.getName());
        personDTO.setLastname(person.getLastname());
        personDTO.setDatebirth(person.getDatebirth());
        return personDTO;
    }

    private Person mapToEntity(final PersonDTO personDTO, final Person person) {
        person.setName(personDTO.getName());
        person.setLastname(personDTO.getLastname());
        person.setDatebirth(personDTO.getDatebirth());
        return person;
    }

    public boolean documentExists(final String document) {
        return personRepository.existsByDocumentIgnoreCase(document);
    }

    public Page<Object[]> findAllPersonFilter(@Valid PaginationDTO paginationDTO) {
        int page = paginationDTO.getPage();
        int limit = paginationDTO.getLimit();
        List<Filter> filters = paginationDTO.getDataFilter();
        boolean endstartDate = false;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        // Constructing the query string
        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Person p ");
        StringBuilder queryBuilderCount = new StringBuilder("SELECT COUNT(p) FROM Person p");
        if (filters.size() > 0) {
            queryBuilder = new StringBuilder("SELECT p FROM Person p WHERE ");
            queryBuilderCount = new StringBuilder("SELECT COUNT(p) FROM Person p WHERE ");
        }
        // Applying filters
        for (int i = 0; i < filters.size(); i++) {
            Filter filter = filters.get(i);
            if (filter.getField() != null && filter.getValue() != null) {
                if (i > 0) {
                    queryBuilder.append(" AND ");

                    queryBuilderCount.append(
                            "AND ");
                }
                if (filter.getFilterType() == FilterType.BETWEEN || filter.getFilterType() == FilterType.NOT_BETWEEN) {
                    endstartDate = true;
                    String[] pivot = filter.getValue().split("->");
                    startDate = LocalDate.parse(pivot[0].trim());
                    endDate = LocalDate.parse(pivot[1].trim());
                }
                // Necesitas ajustar esto dependiendo de la estructura de tu entidad
                queryBuilder.append(buildFilterClause(filter.getField(), filter.getValue(), filter.getFilterType()));
                queryBuilderCount
                        .append(buildFilterClause(filter.getField(), filter.getValue(), filter.getFilterType()));
            }
        }
        if (paginationDTO.getcolumnSort() != null && !paginationDTO.getcolumnSort().isEmpty()) {
            if (paginationDTO.getcolumnSort().contains("-1")) {
                queryBuilder.append(" ORDER BY p." + paginationDTO.getcolumnSort().split("-1")[0] + " DESC");
            } else {
                queryBuilder.append(" ORDER BY p." + paginationDTO.getcolumnSort());
            }
        }
        // Creating the query
        Query query = entityManager.createQuery(queryBuilder.toString());
        if (endstartDate) {
            query.setParameter("date1", startDate);
            query.setParameter("date2", endDate);
        }
        // Setting parameter values for filters
        // for (Filter filter : filters) {
        // if (filter.getField() != null && filter.getValue() != null) {
        // query.setParameter("value", filter.getValue());
        // }
        // }

        // Applying pagination
        query.setFirstResult(page * limit);
        query.setMaxResults(limit);

        // Executing the query to get the result list
        List<Object[]> resultList = query.getResultList();
        Query resultQuery = entityManager.createQuery(queryBuilderCount.toString());
        if (endstartDate) {
            resultQuery.setParameter("date1", startDate);
            resultQuery.setParameter("date2", endDate);
        }
        // Counting total records (without pagination) for constructing the Page object
        Object result = resultQuery.getSingleResult();

        // Convertir el resultado a long
        long totalCount = ((Number) result).longValue();
        // Constructing Page object
        return new PageImpl<>(resultList, PageRequest.of(page, limit), totalCount);

    }

    private String buildFilterClause(String field, String value, FilterType filterType) {
        switch (filterType) {
            case CONTAINS:
                return "p." + field + " LIKE '%" + value + "%'";
            case NOT_CONTAINS:
                return "p." + field + " NOT LIKE '%" + value + "%'";
            case EQUALS:
                return "p." + field + " = '" + value + "'";
            case IS_NULL:
                if (!field.contains("datebirth")) {
                    return "p." + field + " IS NULL OR p." + field + " = ''";
                }
                return "p." + field + " IS NULL";

            case NOT_NULL:
                if (!field.contains("datebirth")) {
                    return "p." + field + " IS NOT NULL AND p." + field + "<> ''";
                }
                return "p." + field + " IS NOT NULL";
            case BETWEEN:
                return "p." + field + " BETWEEN " + ":date1" + " AND "
                        + ":date2";
            case NOT_BETWEEN:

                return "p." + field + " NOT BETWEEN " + ":date1" + " AND "
                        + ":date2";
            case ON_DATE:
                return "p." + field + " = '" + value + "'";
            case NOT_ON_DATE:
                return "p." + field + " <> '" + value + "'";
            default:
                // No se implementan otros tipos de filtros en este ejemplo
                return "";
        }
    }

    private void setParameters(Query query, FilterType filterType, String value, String field) {
        if (filterType == FilterType.BETWEEN || filterType == FilterType.NOT_BETWEEN) {
            String[] values = value.split("->");
            query.setParameter("value1" + field, values[0]);
            query.setParameter("value2" + field, values[1]);
        } else {
            query.setParameter("value" + field, "%" + value + "%"); // Para filtros de tipo LIKE
        }
    }
}

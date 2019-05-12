package ua.dp.ollu.task_accounting;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TaskAccountingApplicationTests {
//    private static final String NAME = "Бег";
//    @Autowired
//    private TaskRepository repository;
//    @Autowired
//    private PersonRepository personRepository;
//    private TaskConverter converter = new TaskConverter.TaskConverterImpl();
//
//    @Before
//    @Transactional
//    public void setUp() throws Exception {
//        Person[] persons = {
//                newPerson(0, "Bill"),
//                newPerson(1, "Still"),
//                newPerson(2, "Jone"),
//                newPerson(3, "Andrea"),
//                newPerson(4, "Julia")
//        };
//
//        List<Person> personList = personRepository.findAll();
//        personList.addAll(Arrays.asList(persons));
//        personRepository.saveAll(personList);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTasksTest() {
//        List<Task> tasks = repository.findAll();
//        assertNotNull(tasks);
//        assertEquals(2, tasks.size());
//        Task task = tasks.get(0);
//
//        Set<PersonsInTask> personsInTasks = task.getPersons();
//        assertNotNull(personsInTasks);
//        assertEquals(3, personsInTasks.size());
//    }
//
//    @Test
//    public void createTaskTest() {
//        long taskId = testTask();
//        Task task = repository.findById(taskId).orElse(null);
//        assertNotNull(task);
//    }
//
//    @Transactional
//    public long testTask() {
////        repository.deleteByName(NAME);
//        Task task = new Task(
//                NAME,
//                converter.parse("2019-05-01"),
//                converter.parse("2019-05-05"));
//
//
//        List<Person> allPersons = personRepository.findAll();
//
//        Task task1 = task;
//        Set<PersonsInTask> personsInTasks = new HashSet<PersonsInTask>() {{
//            add(new PersonsInTask(task1, allPersons.get(0)));
//            add(new PersonsInTask(task1, allPersons.get(2)));
//            add(new PersonsInTask(task1, allPersons.get(3)));
//        }};
//
//        task.setPersons(personsInTasks);
//        task = repository.save(task);
//        task = repository.findById(task.getId()).orElse(null);
//        assertNotNull(task);
//        assertNotNull(task.getPersons());
//        assertEquals(3, task.getPersons().size());
////        repository.delete(task);
//        return task.getId();
//    }
//
//    @Test
////    @Transactional()
//    public void loadTaskTest() {
//        List<Task> tasks = repository.findByName(NAME);
////        repository.delete(tasks.get(0));
//        tasks = repository.findAll();
//        assertNotNull(tasks);
//        for (Task task : tasks) {
//            System.out.println(task.getName());
//        }
//    }
//
//    @Test
//    public void updateTaskTest() {
//        List<Person> peoples = personRepository.findAll();
//        Task task = repository.findById(501L).orElse(null);
//        assertNotNull(task);
//        Set<PersonsInTask> persons = task.getPersons();
//        assertNotNull(persons);
//        persons.add(new PersonsInTask(task, peoples.get(4)));
//        repository.save(task);
//    }
//
//    @Test
//    public void deleteTest() {
//        Task task = repository.findById(501L).orElse(null);
//        repository.delete(task);
//        assertNull(repository.findById(501L).orElse(null));
//    }
//
//    private Person newPerson(long id, String name) {
//        Person person = new Person();
//        person.setId(id);
//        person.setName(name);
//        return person;
//    }
}

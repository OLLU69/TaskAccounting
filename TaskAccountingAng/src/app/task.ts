export class Task {
  id: number;
  startDate = getNowDate();
  endDate = getNowDate();
  name = '';
  persons: PersonInTask[] = [];
  personsStr: string;

  setTask(task: Task): Task {
    this.id = task.id;
    this.startDate = normDate(task.startDate);
    this.endDate = normDate(task.endDate);
    this.name = task.name;
    this.persons = task.persons;
    this.personsStr = this.getPersonsStr();
    return this;
  }

  setPersons(personsToSave: Person[]) {
    this.persons = personsToSave.map(value => {
      const personInTask = new PersonInTask();
      personInTask.person = value;
      return personInTask;
    });
  }

  getPersonsStr(): string {
    return this.persons.map(value => value.person.name).join();
  }
}

function normDate(dateStr: string) {
  return dateStr.substr(0, 10);
}

function getNowDate(): string {
  return normDate(new Date().toISOString());
}

export class PersonInTask {
  id: number = null;
  person: Person;
}

export class Person {
  id: number;
  name: string;
  checked = false;

  set(value: Person): Person {
    this.id = value.id;
    this.name = value.name;
    return this;
  }
}

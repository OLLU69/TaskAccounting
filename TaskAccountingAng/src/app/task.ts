export class Task {
  id: number;
  startDate = getNowDate();
  endDate = getNowDate();
  name = '';
  personsList: Person[] = [];
  persons: string;

  setTask(task: Task
  ): Task {
    this.id = task.id;
    this.startDate = task.startDate;
    this.endDate = task.endDate;
    this.name = task.name;
    this.personsList = task.personsList;
    this.persons = this.getPersons();
    return this;
  }

  getPersons(): string {
    return this.personsList.map(value => value.name).join();
  }
}

export function getNowDate(): string {
  return new Date().toISOString().substr(0, 10);
}

export class Person {
  id: number;
  name: string;
}

export class CheckedPerson extends Person {
  checked = false;

  set(person: Person): CheckedPerson {
    this.id = person.id;
    this.name = person.name;
    return this;
  }
}

import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TaskListComponent} from './task-list/task-list.component';
import {AddTaskComponent} from './task-create/add-task.component';

const routes: Routes = [
  {
    path: 'task_list',
    component: TaskListComponent,
    data: {title: 'Task List'}
  },
  {
    path: 'task_edit/:id',
    component: AddTaskComponent,
    data: {title: 'Task Details'}
  },
  {
    path: 'add_task',
    component: AddTaskComponent,
    data: {title: 'Create Task'}
  },
  {
    path: '',
    redirectTo: '/task_list',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

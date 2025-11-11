import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  //imports: [RouterLink, RouterLinkActive, RouterOutlet, RegistroComponent], unused import
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  constructor(private router: Router){

  }
navigateTo(arg0: string) {
  this.router.navigate([arg0]);
}
}
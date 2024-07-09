import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-nav-sidebar-link',
  template: `<a
    routerLink="{{ link }}"
    routerLinkActive="bg-[#99E1D9] hover:bg-[#99E1D9] font-bold"
    class="block px-3 w-[90%] py-1 rounded-r-full hover:bg-[#f0f7f4]"
    >{{ title }}</a
  >`,
  standalone: true,
  imports: [RouterModule],
})
export class NavSidebarLinkComponent {
  @Input() title: string = '';
  @Input() link: string = '';
}

/*
99E1D9, f0f7f4   green white

*/

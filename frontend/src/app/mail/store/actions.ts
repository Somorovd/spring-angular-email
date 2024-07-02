import { createActionGroup, emptyProps, props } from '@ngrx/store';

import { StatusListResponseInterface } from '../types/statusListResponse.interface';

export const MailActions = createActionGroup({
  source: 'Mail',
  events: {
    // ------------------------------------------------
    'Get Inbox': emptyProps(),
    'Get Inbox Success': props<{ inbox: StatusListResponseInterface }>(),
    'Get Inbox Failed': emptyProps(),
    // ------------------------------------------------
  },
});

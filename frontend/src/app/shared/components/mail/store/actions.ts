import { createActionGroup, emptyProps, props } from '@ngrx/store';

import { InboxStateInterface } from '../types/inboxState.interface';

export const MailActions = createActionGroup({
  source: 'Mail',
  events: {
    // ------------------------------------------------
    'Get Inbox': emptyProps(),
    'Get Inbox Success': props<{ inbox: InboxStateInterface }>(),
    'Get Inbox Failed': emptyProps(),
    // ------------------------------------------------
  },
});

import { createActionGroup, emptyProps, props } from '@ngrx/store';

import { InboxStateInterface } from '../types/inboxState.interface';
import { StatusUpdateRequestInterface } from '../types/statusUpdateRequest.interface';
import { Email } from '../../shared/types/email.interface';
import { Status } from '../../shared/types/status.interface';

export const MailActions = createActionGroup({
  source: 'Mail',
  events: {
    // ------------------------------------------------
    'Get Inbox': emptyProps(),
    'Get Inbox Success': props<{ inbox: InboxStateInterface }>(),
    'Get Inbox Failed': emptyProps(),
    // ------------------------------------------------
    'Get Status': props<{ statusId: number }>(),
    'Get Status Success': props<{ status: Status }>(),
    'Get Status Failed': emptyProps(),
    // ------------------------------------------------
    'Get Chain': props<{ chainId: number }>(),
    'Get Chain Success': props<{ emails: Email[] }>(),
    'Get Chain Failed': emptyProps(),
    // ------------------------------------------------
    'Update Status': props<{
      statusId: number;
      statusUpdate: StatusUpdateRequestInterface;
    }>(),
    'Update Status Success': props<{ status: Status }>(),
    'Update Status Failed': emptyProps(),
    // ------------------------------------------------
  },
});

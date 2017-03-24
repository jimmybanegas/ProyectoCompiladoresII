format PE CONSOLE 4.0
entry start

include 'win32a.inc'

section '.data' data readable writeable

       str_pause db  'pause' ,0
        @intprintstr db 'Resultado: %d' ,10,0
     @intscanstr db '%d',0
		temp1 dd 0
		@a@ dd 0
		@b@ dd 0
		@c@ dd 0
		@d@ dd 0
		@e@ dd 0
		@x@ db 0


section '.code' code readable executable

  start: 
		mov eax,1
		add eax,3
		mov [temp1],eax
		mov eax,[temp1]
		mov [@e@],eax
		
		mov eax,0
		mov [@a@],eax
		
		mov eax,5
		mov [@b@],eax
		
		push [@e@]
		push @intprintstr
		call [printf]
		add esp,8
    
;Finalizar el proceso
      push str_pause
      call [system]
      add esp, 4
      call [ExitProcess]   

section '.idata' import data readable writeable

  library kernel,'KERNEL32.DLL',\
          ms ,'msvcrt.dll'

  import kernel,\
         ExitProcess,'ExitProcess'

  import ms,\
         printf,'printf',\
         cget ,'_getch',\
         system,'system',\
         scanf,'scanf'
                          
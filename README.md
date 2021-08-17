# person-spendings
 egyéni költségeket nyilvántartó rendszer
Két entitás:
  - Person:
          - TAJ szám
          - név
              - Spending lista
              - költések összege
          
  - Spending:
        - dátum
        - termék vagy szolgáltatás neve
        - költés összege
        
Mindegyik külön táblában és OneToMany kapcsolat van a Person->Spending között.

Validáció:
  Person:
          - TAJ számra saját validáció létrehozva
          - név nem lehet null, üres, két karakternél rövidebb, és ötven karakternél több
  Spending:
          - dátum nem lehet null
          - termék/szolgáltatás neve nem lehet null, üres vagy száz karakternél hosszabb
          - költés összege csak pozitív lehet

Hibakezelés: nem létező id-re történő lekérdezéseknél, módosításoknál IllegalArgumentException-nal kezelve

Végpontok:
    - /api/persons
      GET-el lehet lekérni az összes persont listában
      POST-al lehet új persont létrehozni, ha már létezik (TAJ számra ellenőrizve) akkor nem hoz létre újat, a már létezőt adja vissza
      DELETE-vel lehet törölni az összes persont
    
    - /api/persons/{id}
    ID alapján:
      GET-el lehet lekérni az adott persont
      PUT-al lehet az adott person nevét megváltoztatni
      POST-al lehet új spending-et az adott person-hoz hozzáadni
      DELETE-vel lehet az adott persont törölni

    - /api/expenditures
      GET-el lehet lekérni az összes költést listában
      POST-al lehet új költést létrehozni
      DELETE-vel lehet törölni az összes költést
      
    - /api/expenditures/{id}
    ID alapján:
      GET-el lehet lekérni az adott költést
      PUT-al lehet az adott költés összegét megváltoztatni (ekkor ha tartozik hozzá person, akkor az adott person összköltését is beállítja)
      DELETE-vel lehet az adott költést törölni
      
    - /api/expenditures/persons/{id}
      GET-el person id-je alapján az adott person összköltéseit adja vissza listában
       
    - /api/expenditures/persons/{id}/betweencosts/
      GET-el person id-je alapján az adott person min és max összeg közötti költéseit kapjuk vissza listában
      
    - /api/expenditures/sumofcostinyearormonth/}
      GET-el adott év adott hónapjának költéseinek összköltését kapjuk vissza egész számként
      
Két repository, két service és két kontroller

Tesztek:
  - RestTemplate tesztek mindkét kontrollerre
  - DtaJpaTest tesztek mindkét service-re
  
  Tesztlefedettség:
    - Class: 100% (8/8)
    - Method: 88% (46/52)
    - Line 88% (87/98)
